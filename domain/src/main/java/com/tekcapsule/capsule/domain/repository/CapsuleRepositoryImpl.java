package com.tekcapsule.capsule.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.capsule.domain.model.Capsule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CapsuleRepositoryImpl implements CapsuleDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public CapsuleRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Capsule> findAll() {

        return dynamo.scan(Capsule.class,new DynamoDBScanExpression());

    }

    @Override
    public Capsule findBy(String capsuleId) {
        return dynamo.load(Capsule.class, capsuleId);
    }

    @Override
    public Capsule save(Capsule capsule) {
        dynamo.save(capsule);
        return capsule;
    }

    @Override
    public Capsule findBy(String hashKey, String rangeKey) {
        return dynamo.load(Capsule.class, hashKey, rangeKey);
    }
}
