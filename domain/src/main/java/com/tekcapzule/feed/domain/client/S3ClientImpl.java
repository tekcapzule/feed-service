package com.tekcapzule.feed.domain.client;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.tekcapzule.feed.domain.exception.FeedCreationException;
import com.tekcapzule.feed.domain.exception.S3ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class S3ClientImpl implements S3Client {

    private AmazonS3 amazonS3;

    public S3ClientImpl(final AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }
    public Bucket getBucket(String bucket_name) {
        Bucket named_bucket = null;
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    @Override
    public String getResourceUrl(String extImageS3Bucket, String imageName) {
        AmazonS3Client amazonS3Client = (AmazonS3Client) amazonS3;
        return amazonS3Client.getResourceUrl(extImageS3Bucket, imageName);
    }

    public void putS3InputStream(String bucketName, String objectKey,
                                 InputStream in, int contentLength) throws FeedCreationException{
        try {
            log.info(String.format("Entering puts3InputStream - Uploading object %s to bucket %s", objectKey, bucketName));
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            PutObjectRequest putOb = new PutObjectRequest(bucketName, objectKey, in, objectMetadata);
            amazonS3.putObject(putOb);
            log.info(String.format("Successfully placed %s into bucket %s", objectKey, bucketName));

        } catch (AmazonS3Exception e) {
            log.error("Error uploading image, error connecting S3");
            throw new S3ClientException(e.getMessage(), e);
        }
    }

}
