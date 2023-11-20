package com.tekcapzule.feed.domain.client;

import com.amazonaws.services.s3.model.Bucket;

import java.io.InputStream;

public interface S3Client {
    void putS3InputStream(String bucketName, String objectKey, InputStream in, int contentLength);
    Bucket getBucket(String bucket_name);
}
