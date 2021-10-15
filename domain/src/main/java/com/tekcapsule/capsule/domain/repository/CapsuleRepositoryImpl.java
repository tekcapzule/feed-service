package com.tekcapsule.capsule.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.query.SearchItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CapsuleRepositoryImpl implements CapsuleDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public CapsuleRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Capsule> findAll(String tenantId) {

        Capsule hashKey = Capsule.builder().tenantId(tenantId).build();
        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withHashKeyValues(hashKey);

        return dynamo.query(Capsule.class, queryExpression);
    }

    @Override
    public Capsule findBy(String tenantId, String userId) {
        return dynamo.load(Capsule.class, tenantId, userId);
    }

    @Override
    public Capsule save(Capsule capsule) {
        dynamo.save(capsule);
        return capsule;
    }

    @Override
    public void delete(String tenantId, String id) {
        Capsule capsule = findBy(tenantId, id);
        if (capsule != null) {
            dynamo.delete(capsule);
        }
    }

    @Override
    public void disableById(String tenantId, String id) {
        Capsule capsule = findBy(tenantId, id);
        if (capsule != null) {
            capsule.setActive(false);
            dynamo.save(capsule);
        }
    }

    @Override
    public List<SearchItem> search(String tenantId) {
        Capsule hashKey = Capsule.builder().tenantId(tenantId).build();
        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withHashKeyValues(hashKey);
        List<Capsule> capsules = dynamo.query(Capsule.class, queryExpression);
        List<SearchItem> searchItems = new ArrayList<SearchItem>();
        if (capsules != null) {
            searchItems = capsules.stream().map(capsule -> {
                return SearchItem.builder()
                        .activeSince(capsule.getActiveSince())
                        .headLine(capsule.getHeadLine())
                        .name(capsule.getName())
                        .photoUrl(capsule.getPhotoUrl())
                        .rating(capsule.getRating())
                        .social(capsule.getSocial())
                        .build();
            }).collect(Collectors.toList());
        }
        return searchItems;
    }
}
