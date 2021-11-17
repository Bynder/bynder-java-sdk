package com.bynder.sdk.util;

import com.bynder.sdk.exception.HttpResponseException;
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

    public static <T> Response<T> ensureSuccessResponse(Response<T> response)
            throws HttpResponseException {
        if (!response.isSuccessful()) {
            throw new HttpResponseException(response);
        }
        return response;
    }

    public static <T> T getResponseBody(Response<T> response)
            throws HttpResponseException {
        return ensureSuccessResponse(response).body();
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
