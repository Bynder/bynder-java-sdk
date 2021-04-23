package com.bynder.sdk.service.amazons3;

import com.bynder.sdk.model.upload.MultipartParameters;
import com.bynder.sdk.model.upload.S3File;
import com.bynder.sdk.model.upload.UploadRequest;
import io.reactivex.Observable;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class AmazonS3ServiceImplTest {


    @Test
    public void verifyOrderOfParams() {
        AmazonS3ServiceImpl impl = new AmazonS3ServiceImpl("http://bucket");
        impl.amazonS3Api = params -> {
            Iterator<String> i = params.keySet().iterator();
            assertEquals("x-amz-credential", i.next());
            assertEquals("key", i.next());
            assertEquals("Policy", i.next());
            assertEquals("X-Amz-Signature", i.next());
            assertEquals("acl", i.next());
            assertEquals("x-amz-algorithm", i.next());
            assertEquals("x-amz-date", i.next());
            assertEquals("success_action_status", i.next());
            assertEquals("Content-Type", i.next());
            assertEquals("name", i.next());

            assertEquals("chunk", i.next());
            assertEquals("chunks", i.next());
            assertEquals("Filename", i.next());

            assertEquals("file", i.next());
            return Observable.empty();
        };

        MultipartParameters multipartParams = new MultipartParameters("", "", "","","","","","","");
        UploadRequest uploadRequest = new UploadRequest("filename", new S3File(), multipartParams);
        byte[] file = "Randomtext".getBytes(StandardCharsets.UTF_8);
        impl.uploadPartToAmazon("filename", uploadRequest, 1, file, 1);
    }


}