package com.bynder.sdk.util;

import com.bynder.sdk.exception.BynderRequestError;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Utilities to make working with ReactiveX more convenient.
 */
public class RXUtils {

    /**
     * Adds an index to each emitted item in an observable.
     *
     * @param observable original observable
     * @param startFrom starting index
     * @param <T> the type of emitted items
     * @return observable that emits indexed items
     */
    public static <T> Observable<Indexed<T>> mapWithIndex(Observable<T> observable, int startFrom) {
        return observable.zipWith(
                Observable.range(startFrom, Integer.MAX_VALUE - startFrom),
                Indexed::new
        );
    }

    /**
     * Adds an index to each emitted item in an observable, starting from 0.
     *
     * @param observable original observable
     * @param <T> the type of emitted items
     * @return observable that emits indexed items
     */
    public static <T> Observable<Indexed<T>> mapWithIndex(Observable<T> observable) {
        return mapWithIndex(observable, 0);
    }

    private static <T> Single<Response<T>> throwOnFailureResponse(Single<Response<T>> request) {
        return request.map(response -> {
            if (!response.isSuccessful()) {
                throw new BynderRequestError(response);
            }
            return response;
        });
    }

    /**
     * Wraps a response in a new Completable, ignoring the request and
     * emitting an error when the response was not successful.
     *
     * @param request request to the Bynder API
     * @param <T> type the response body is mapped to
     * @return Completable handling the request
     */
    public static <T> Completable handleResponse(Single<Response<T>> request) {
        return throwOnFailureResponse(request).ignoreElement();
    }

    /**
     * Wraps a response in a new Completable, ignoring the request and
     * emitting an error when the response was not successful.
     *
     * @param request request to the Bynder API
     * @param <T> type the response body is mapped to
     * @return Completable handling the request
     */
    public static <T> Completable handleResponse(Observable<Response<T>> request) {
        return handleResponse(request.singleOrError());
    }

    /**
     * Wraps a response in a new Single, emitting an error when the response
     * was not successful.
     *
     * @param request request to the Bynder API
     * @param <T> type the response body is mapped to
     * @return a Single handling the request, emitting the response body
     */
    public static <T> Single<T> handleResponseBody(Single<Response<T>> request) {
        return throwOnFailureResponse(request).map(Response::body);
    }

    /**
     * Wraps a response in a new Single, emitting an error when the response
     * was not successful.
     *
     * @param request request to the Bynder API
     * @param <T> type the response body is mapped to
     * @return a Single handling the request, emitting the response body
     */
    public static <T> Single<T> handleResponseBody(Observable<Response<T>> request) {
        return handleResponseBody(request.singleOrError());
    }

    /**
     * Reads a file into chunks and emits them through an Observable.
     *
     * @param path path to the file
     * @param maxChunkSize maximum chunk size in bytes
     * @return Observable emitting file chunks
     */
    public static Observable<byte[]> readFileChunks(String path, int maxChunkSize) {
        return Observable.using(
                () -> new BufferedInputStream(new FileInputStream(path), maxChunkSize),
                is -> Observable.generate(emitter -> {
                    byte[] buffer = new byte[maxChunkSize];
                    int chunkSize = is.read(buffer);
                    if (chunkSize == -1) {
                        // Reading chunks completed.
                        is.close();
                        emitter.onComplete();
                    } else if (chunkSize < maxChunkSize) {
                        // The last chunk could be smaller than the max size.
                        emitter.onNext(Arrays.copyOf(buffer, chunkSize));
                    } else {
                        emitter.onNext(buffer);
                    }
                }),
                BufferedInputStream::close
        );
    }

    /**
     * Reads a file and emits its content.
     *
     * @param path path to the file
     * @return Single emitting the file content.
     */
    public static Single<byte[]> readFile(String path) {
        // Read the whole file into one chunk.
        return readFileChunks(
                path,
                (int) new File(path).length()
        ).singleOrError();
    }

}
