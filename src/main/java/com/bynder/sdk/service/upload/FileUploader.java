package com.bynder.sdk.service.upload;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.upload.PrepareUploadResponse;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.*;
import com.bynder.sdk.util.Indexed;
import com.bynder.sdk.util.RXUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import org.apache.commons.codec.digest.DigestUtils;

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

    /**
     * Creates a new instance of the class.
     *
     * @param bynderApi    Instance to handle the HTTP communication with the Bynder API.
     * @param queryDecoder Query decoder.
     */
    public FileUploader(
            final BynderApi bynderApi,
            final QueryDecoder queryDecoder
    ) {
        this.bynderApi = bynderApi;
        this.queryDecoder = queryDecoder;
    }

    /**
     * Uploads a file with the information specified in the query parameter.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    public Single<SaveMediaResponse> uploadFile(final NewAssetUploadQuery uploadQuery) {
        return uploadToFilesService(uploadQuery).flatMap(fileId -> saveMedia(fileId, uploadQuery));
    }

    public Single<SaveMediaResponse> uploadFile(final ExistingAssetUploadQuery uploadQuery) {
        return uploadToFilesService(uploadQuery).flatMap(fileId -> saveMedia(fileId, uploadQuery));
    }

    private Single<UUID> uploadToFilesService(final UploadQuery uploadQuery) {
        return prepareUpload().flatMap(fileId ->
                RXUtils.mapWithIndex(
                        RXUtils.readFileChunks(uploadQuery.getFilepath(), MAX_CHUNK_SIZE)
                ).flatMapSingle(chunk ->
                        uploadChunk(fileId, chunk).toSingle(() -> chunk)
                ).toList().flatMapCompletable(chunks ->
                        finalize(fileId, chunks.size(), uploadQuery)
                ).andThen(Single.just(fileId))
        );
    }

    private Single<UUID> prepareUpload() {
        return bynderApi.prepareUpload()
                .map(RXUtils::getResponseBody)
                .map(PrepareUploadResponse::getFileId);
    }

    private Completable uploadChunk(final UUID fileId, final Indexed<byte[]> chunk) {
        return bynderApi.uploadChunk(
                DigestUtils.sha256Hex(chunk.getValue()),
                fileId,
                chunk.getIndex(),
                RequestBody.create(chunk.getValue())
        ).map(RXUtils::ensureSuccessResponse).ignoreElement();
    }

    private Completable finalize(final UUID fileId, final int numberOfChunks, final UploadQuery uploadQuery) {
        return RXUtils.readFile(
                uploadQuery.getFilepath()
        ).flatMapCompletable(fileContent -> bynderApi.finaliseUpload(
                fileId,
                queryDecoder.decode(new FinaliseUploadQuery(
                        numberOfChunks,
                        uploadQuery.getFilename(),
                        fileContent.length,
                        DigestUtils.sha256Hex(fileContent)
                ))
        ).map(RXUtils::ensureSuccessResponse).ignoreElement());
    }

    private Single<SaveMediaResponse> saveMedia(final UUID fileId, final NewAssetUploadQuery uploadQuery) {
        // A new asset will be created for the uploaded file.
        return bynderApi.saveMedia(
                fileId,
                queryDecoder.decode(new SaveMediaQuery()
                        .setBrandId(uploadQuery.getBrandId())
                        .setName(uploadQuery.getFilename())
                        .setAudit(uploadQuery.isAudit())
                        .setTags(uploadQuery.getTags())
                        .setMetaproperties(uploadQuery.getMetaproperties()))
        ).map(RXUtils::getResponseBody);
    }

    private Single<SaveMediaResponse> saveMedia(final UUID fileId, final ExistingAssetUploadQuery uploadQuery) {
        // The uploaded file will be attached to an existing asset.
        return bynderApi.saveMedia(
                uploadQuery.getMediaId(),
                fileId
        ).map(RXUtils::getResponseBody);
    }

}
