package com.tekcapzule.feed.domain.client;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
        //final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_NORTH_1).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }
    public void putS3InputStream(String bucketName, String objectKey,
                                 InputStream in, int contentLength) throws FeedCreationException{
        try {
            log.info("Entering puts3InputStream - Uploading object %s to bucket %s", objectKey, bucketName);
            //final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            PutObjectRequest putOb = new PutObjectRequest(bucketName, objectKey, in, objectMetadata);

            amazonS3.putObject(putOb);
            log.info("Successfully placed %s into bucket %s", objectKey, bucketName);

        } catch (AmazonS3Exception e) {
            log.error("Error uploading image, error connecting S3");
            throw new S3ClientException(e.getMessage(), e);
        }
    }

}
