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
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Response;

import java.util.Map;
import java.util.UUID;

public class FileUploader {

    static final int MAX_CHUNK_SIZE = 1024 * 1024 * 5;

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication.
     */
    private final BynderApi bynderApi;

    /**
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;

    private final UploadQuery uploadQuery;

    /**
     * Creates a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     * @param queryDecoder Query decoder.
     * @param uploadQuery Upload query with the information to upload the file.
     */
    public FileUploader(
            final BynderApi bynderApi,
            final QueryDecoder queryDecoder,
            final UploadQuery uploadQuery
    ) {
        this.bynderApi = bynderApi;
        this.queryDecoder = queryDecoder;
        this.uploadQuery = uploadQuery;
    }

    /**
     * Uploads a file with the information specified in the query parameter.
     *
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    public Single<SaveMediaResponse> uploadFile() {
        return prepareUpload().flatMap(fileId ->
            RXUtils.mapWithIndex(
                    RXUtils.readFileChunks(uploadQuery.getFilepath(), MAX_CHUNK_SIZE)
            ).flatMapSingle(chunk -> uploadChunk(fileId, chunk)).toList()
                    .flatMapCompletable(chunks -> finalize(fileId, chunks.size()))
                    .andThen(saveMedia(fileId)
            )
        );
    }

    /**
     * Check {@link BynderApi#prepareUpload()} for more information.
     *
     * @return ID of the file to be uploaded
     */
    private Single<UUID> prepareUpload() {
        return RXUtils.handleResponseBody(bynderApi.prepareUpload())
                .map(PrepareUploadResponse::getFileId);
    }

    /**
     * Check {@link BynderApi#uploadChunk(String, UUID, int, RequestBody)} for more information.
     *
     * @param chunk part of the file to be uploaded
     * @return the uploaded chunk
     */
    private Single<Indexed<byte[]>> uploadChunk(final UUID fileId, final Indexed<byte[]> chunk) {
        int chunkNumber = chunk.getIndex();
        byte[] bytes = chunk.getValue();

        return RXUtils.handleResponse(bynderApi.uploadChunk(
                DigestUtils.sha256Hex(bytes),
                fileId,
                chunkNumber,
                RequestBody.create(bytes)
        )).map(response -> chunk);
    }

    /**
     * Check {@link BynderApi#finaliseUpload(UUID, Map)} for more information.
     *
     * @return the correlation ID to check the processing status of the uploaded file
     */
    private Completable finalize(final UUID fileId, final int numberOfChunks) {
        return Completable.fromSingle(
                RXUtils.readFile(
                        uploadQuery.getFilepath()
                ).flatMap(fileContent -> RXUtils.handleResponse(bynderApi.finaliseUpload(
                        fileId,
                        queryDecoder.decode(new FinaliseUploadQuery(
                                numberOfChunks,
                                uploadQuery.getFilename(),
                                fileContent.length,
                                DigestUtils.sha256Hex(fileContent)
                        ))
                )))
        );
    }

    /**
     * Calls {@link BynderApi#saveMedia} to save the completely uploaded file in
     * Bynder.
     *
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    private Single<SaveMediaResponse> saveMedia(final UUID fileId) {
        Single<Response<SaveMediaResponse>> response;

        if (uploadQuery.getMediaId() != null) {
            // The uploaded file will be attached to an existing asset.
            response = bynderApi.saveMedia(
                    uploadQuery.getMediaId(),
                    fileId
            );
        } else {
            // A new asset will be created for the uploaded file.
            SaveMediaQuery saveMediaQuery = new SaveMediaQuery()
                    .setBrandId(uploadQuery.getBrandId())
                    .setName(uploadQuery.getFilename())
                    .setAudit(uploadQuery.isAudit())
                    .setMetaproperties(uploadQuery.getMetaproperties());
            response = bynderApi.saveMedia(
                    fileId,
                    queryDecoder.decode(saveMediaQuery)
            );
        }

        return RXUtils.handleResponseBody(response);
    }

}
