package com.bynder.sdk.util;

import com.bynder.sdk.exception.HttpResponseException;
import io.reactivex.Observable;
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

    @Before
    public void setUp() {
        observable = Observable.fromArray("foo", "bar", "baz");
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
    public void handleSuccessResponse()
            throws HttpResponseException {
        assertEquals(SUCCESS_RESPONSE, RXUtils.ensureSuccessResponse(SUCCESS_RESPONSE));
    }

    @Test
    public void handleFailureResponse() {
        try {
            RXUtils.ensureSuccessResponse(SUCCESS_RESPONSE);
        } catch(HttpResponseException e) {
            assertEquals(FAILURE_RESPONSE.toString(), e.getMessage());
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
