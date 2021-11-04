package com.tekcapsule.capsule.application;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.capsule","com.tekcapsule.core.config"})
@EnableDynamoDBRepositories(dynamoDBMapperConfigRef = "getDynamoDBMapperConfig", basePackages ={ "com.tekcapsule.core.config","com.tekcapsule.capsule.domain.repository"})
public class CapsuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsuleApplication.class, args);
    }
}