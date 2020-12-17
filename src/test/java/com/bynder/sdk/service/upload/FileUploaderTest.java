package com.bynder.sdk.service.upload;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.upload.PrepareUploadResponse;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.FinaliseUploadQuery;
import com.bynder.sdk.query.upload.SaveMediaQuery;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.util.Indexed;
import com.bynder.sdk.util.RXUtils;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUploaderTest {

    private static final UUID FILE_ID = UUID.randomUUID();
    private static final List<String> CHUNK_STRINGS = Arrays.asList("foo", "bar", "baz");
    private static final List<byte[]> CHUNKS = CHUNK_STRINGS.stream()
            .map(s -> s.getBytes(StandardCharsets.UTF_8))
            .collect(Collectors.toList());
    private static final List<RequestBody> CHUNK_BODIES = CHUNKS.stream()
            .map(RequestBody::create)
            .collect(Collectors.toList());
    private static final String FILE_CONTENT_STRING = String.join("", CHUNK_STRINGS);
    private static final byte[] FILE_CONTENT = FILE_CONTENT_STRING.getBytes(StandardCharsets.UTF_8);
    private static final String EXISTING_MEDIA_ID = "EXISTING_MEDIA_ID";
    private static final String FILE_PATH = "/path/to/file.ext";
    private static final SaveMediaResponse SAVE_MEDIA_RESPONSE = new SaveMediaResponse();

    private QueryDecoder queryDecoder;

    @Mock
    private BynderApi bynderApiMock;

    private MockedStatic<RXUtils> rxUtilsMock;
    private MockedStatic<RequestBody> requestBodyMock;
    private MockedStatic<DigestUtils> digestUtilsMock;

    @Before
    public void setUp() {
        queryDecoder = new QueryDecoder();

        List<Indexed<byte[]>> chunks = new ArrayList<>();
        for (int i = 0; i < CHUNKS.size(); i++) {
            chunks.add(new Indexed<>(CHUNKS.get(i), i));
        }

        PrepareUploadResponse prepareUploadResponseMock = mock(PrepareUploadResponse.class);
        when(prepareUploadResponseMock.getFileId())
                .thenReturn(FILE_ID);
        when(bynderApiMock.prepareUpload())
                .thenReturn(Single.just(Response.success(prepareUploadResponseMock)));

        when(bynderApiMock.uploadChunk(anyString(), any(UUID.class), anyInt(), any(RequestBody.class)))
                .thenReturn(Single.just(Response.success(null)));

        when(bynderApiMock.finaliseUpload(any(UUID.class), any()))
                .thenReturn(Single.just(Response.success(null)));

        when(bynderApiMock.saveMedia(any(UUID.class), any()))
                .thenReturn(Single.just(Response.success(SAVE_MEDIA_RESPONSE)));
        when(bynderApiMock.saveMediaVersion(anyString(), any(UUID.class), any()))
                .thenReturn(Single.just(Response.success(SAVE_MEDIA_RESPONSE)));

        rxUtilsMock = mockStatic(RXUtils.class);
        rxUtilsMock.when(() -> RXUtils.handleResponse(ArgumentMatchers.<Single<Response<Object>>>any()))
                .then(returnsFirstArg());
        rxUtilsMock.when(() -> RXUtils.handleResponseBody(ArgumentMatchers.<Single<Response<Object>>>any()))
                .thenAnswer(i -> i.<Single<Response<Object>>>getArgument(0).map(Response::body));
        rxUtilsMock.when(() -> RXUtils.mapWithIndex(any()))
                .thenReturn(Observable.fromIterable(chunks));
        rxUtilsMock.when(() -> RXUtils.readFile(anyString()))
                .thenReturn(Single.just(FILE_CONTENT));

        requestBodyMock = mockStatic(RequestBody.class);
        for (int i = 0; i < CHUNKS.size(); i++) {
            byte[] chunk = CHUNKS.get(i);
            requestBodyMock.when(() -> RequestBody.create(chunk))
                    .thenReturn(CHUNK_BODIES.get(i));
        }

        digestUtilsMock = mockStatic(DigestUtils.class);
        digestUtilsMock.when(() -> DigestUtils.sha256Hex(any(byte[].class)))
                .thenAnswer(i -> new String(i.getArgument(0), StandardCharsets. UTF_8));
    }

    @After
    public void tearDown() {
        rxUtilsMock.close();
        requestBodyMock.close();
        digestUtilsMock.close();
    }

    @Test
    public void uploadFileForNewAsset() {
        UploadQuery uploadQuery = new UploadQuery(FILE_PATH)
                .setBrandId("SOME_BRAND_ID");
        assertEquals(SAVE_MEDIA_RESPONSE, uploadFile(uploadQuery));
        verify(bynderApiMock).saveMedia(
                FILE_ID,
                queryDecoder.decode(new SaveMediaQuery()
                        .setName(uploadQuery.getFilename())
                        .setAudit(uploadQuery.isAudit())
                        .setMetaproperties(uploadQuery.getMetaproperties())
                        .setBrandId(uploadQuery.getBrandId())
                )
        );
    }

    @Test
    public void uploadFileForExistingAsset() {
        UploadQuery uploadQuery = new UploadQuery(FILE_PATH).setMediaId(EXISTING_MEDIA_ID);
        assertEquals(SAVE_MEDIA_RESPONSE, uploadFile(uploadQuery));
        verify(bynderApiMock).saveMediaVersion(
                EXISTING_MEDIA_ID,
                FILE_ID,
                queryDecoder.decode(new SaveMediaQuery()
                        .setName(uploadQuery.getFilename())
                        .setAudit(uploadQuery.isAudit())
                        .setMetaproperties(uploadQuery.getMetaproperties())
                )
        );
    }

    private SaveMediaResponse uploadFile(UploadQuery uploadQuery) {
        SaveMediaResponse response = new FileUploader(bynderApiMock, queryDecoder, uploadQuery)
                .uploadFile().blockingGet();

        verify(bynderApiMock).prepareUpload();

        for (int i = 0; i < CHUNKS.size(); i++) {
            verify(bynderApiMock).uploadChunk(
                    CHUNK_STRINGS.get(i),
                    FILE_ID,
                    i,
                    CHUNK_BODIES.get(i)
            );
        }

        verify(bynderApiMock).finaliseUpload(
                FILE_ID,
                queryDecoder.decode(new FinaliseUploadQuery(
                        CHUNKS.size(),
                        uploadQuery.getFilename(),
                        FILE_CONTENT.length,
                        FILE_CONTENT_STRING
                ))
        );

        return response;
    }

}
