package com.tekcapzule.feed.domain.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AWSConfig {
    private Environment environment;

    public AWSConfig(Environment environment){
        this.environment = environment;
    }

    public AWSCredentials credentials() {
        this.environment = environment;
        AWSCredentials credentials = new BasicAWSCredentials(
                environment.getProperty("aws.accesskey"),
                environment.getProperty("aws.secretey"));
        return credentials;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new InstanceProfileCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();
        return s3client;
    }

}