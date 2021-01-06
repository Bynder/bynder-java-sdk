package com.bynder.sdk.util;

import com.bynder.sdk.exception.BynderRequestError;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RXUtilsTest {

    public static final Response<String> SUCCESS_RESPONSE = Response.success("Great success!");
    public static final Response<String> FAILURE_RESPONSE = Response.error(
            500,
            ResponseBody.create("Epic fail!", MediaType.parse("text/plain"))
    );

    private Observable<String> observable;
    private Single<Response<String>> successResponse;
    private Single<Response<String>> failureResponse;

    @Before
    public void setUp() {
        observable = Observable.fromArray("foo", "bar", "baz");
        successResponse = Single.just(SUCCESS_RESPONSE);
        failureResponse = Single.just(FAILURE_RESPONSE);
    }

    @Test
    public void mapWithIndexWithStartFrom() {
        List<Indexed<String>> expected = Arrays.asList(
                new Indexed<>("foo", 42),
                new Indexed<>("bar", 43),
                new Indexed<>("baz", 44)
        );
        List<Indexed<String>> actual = RXUtils.mapWithIndex(observable, 42)
                .toList().blockingGet();
        assertEquals(expected, actual);
    }

    @Test
    public void mapWithIndex() {
        List<Indexed<String>> expected = Arrays.asList(
                new Indexed<>("foo", 0),
                new Indexed<>("bar", 1),
                new Indexed<>("baz", 2)
        );
        List<Indexed<String>> actual = RXUtils.mapWithIndex(observable)
                .toList().blockingGet();
        assertEquals(expected, actual);
    }

    @Test
    public void handleSuccessResponseWithSingle() {
        RXUtils.handleResponse(successResponse)
                .blockingAwait();
    }

    @Test
    public void handleFailureResponseWithSingle() {
        try {
            RXUtils.handleResponse(failureResponse)
                    .blockingAwait();
        } catch(RuntimeException e) {
            assertEquals(BynderRequestError.class, e.getCause().getClass());
            assertEquals(FAILURE_RESPONSE.toString(), e.getCause().getMessage());
        }
    }

    @Test
    public void handleSuccessResponseWithObservable() {
        RXUtils.handleResponse(successResponse.toObservable())
                .blockingAwait();
    }

    @Test
    public void handleFailureResponseWithObservable() {
        try {
            RXUtils.handleResponse(failureResponse.toObservable())
                    .blockingAwait();
        } catch(RuntimeException e) {
            assertEquals(BynderRequestError.class, e.getCause().getClass());
            assertEquals(FAILURE_RESPONSE.toString(), e.getCause().getMessage());
        }
    }

    @Test
    public void handleSuccessResponseBodyWithSingle() {
        String actual = RXUtils.handleResponseBody(successResponse)
                .blockingGet();
        assertEquals(SUCCESS_RESPONSE.body(), actual);
    }

    @Test
    public void handleFailureResponseBodyWithSingle() {
        try {
            String actual = RXUtils.handleResponseBody(failureResponse)
                    .blockingGet();
        } catch(RuntimeException e) {
            assertEquals(BynderRequestError.class, e.getCause().getClass());
            assertEquals(FAILURE_RESPONSE.toString(), e.getCause().getMessage());
        }
    }

    @Test
    public void handleSuccessResponseBodyWithObservable() {
        String actual = RXUtils.handleResponseBody(successResponse.toObservable())
                .blockingGet();
        assertEquals(SUCCESS_RESPONSE.body(), actual);
    }

    @Test
    public void handleFailureResponseBodyWithObservable() {
        try {
            String actual = RXUtils.handleResponseBody(failureResponse.toObservable())
                    .blockingGet();
        } catch(RuntimeException e) {
            assertEquals(BynderRequestError.class, e.getCause().getClass());
            assertEquals(FAILURE_RESPONSE.toString(), e.getCause().getMessage());
        }
    }

    @Test
    public void readFileChunks() throws IOException {
        String path = ClassLoader.getSystemResource("config.properties").getPath();
        int maxChunkSize = 5;

        Iterator<byte[]> chunks = RXUtils.readFileChunks(path, maxChunkSize)
                .blockingIterable().iterator();

        byte[] buffer = new byte[maxChunkSize];
        int chunkSize;
        try (FileInputStream is = new FileInputStream(path)) {
            while ((chunkSize = is.read(buffer)) != -1) {
                byte[] expectedChunk = Arrays.copyOf(buffer, chunkSize);
                byte[] actualChunk = chunks.next();
                assertArrayEquals(expectedChunk, actualChunk);
            }
        }
    }

    @Test
    public void readFile() throws IOException {
        String path = ClassLoader.getSystemResource("config.properties").getPath();
        byte[] expected = Files.readAllBytes(Paths.get(path));
        byte[] actual = RXUtils.readFile(path).blockingGet();
        assertArrayEquals(expected, actual);
    }

}
