package com.tekcapsule.capsule.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.capsule.domain.model.Capsule;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CapsuleRepositoryImpl implements CapsuleDynamoRepository {

    private DynamoDBMapper dynamo;

    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String STATUS_KEY = "status";


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

    @Override
    public List<Capsule> findAllFeeds(List<String> subscribedTopics) {

        List<Capsule> capsules = new ArrayList<>();

        subscribedTopics.forEach(topic -> {
            var myCapsules = queryCapsules(QueryCriteria.builder()
                    .indexName("topicGSI")
                    .hashKeyName(STATUS_KEY)
                    .hashKeyValue(ACTIVE_STATUS)
                    .rangeKeyName("topicCode")
                    .rangeKeyValue(topic)
                    .build());
            if (myCapsules != null && myCapsules.size() > 0)
            { capsules.addAll(myCapsules);}
        });
     return capsules;
    }

    @Override
    public List<Capsule> findAllEditorsPick() {
        return queryCapsules( QueryCriteria.builder()
                .indexName("editorsPickGSI")
                .hashKeyName(STATUS_KEY)
                .hashKeyValue(ACTIVE_STATUS)
                .rangeKeyName("editorsPick")
                .rangeKeyValue("1")
                .build());
    }

    @Override
    public List<Capsule> findAllTrending() {
        return queryCapsules( QueryCriteria.builder()
                .indexName("trendingGSI")
                .hashKeyName(STATUS_KEY)
                .hashKeyValue(ACTIVE_STATUS)
                .rangeKeyName("recommendations")
                .rangeKeyValue("1")
                .build());
    }

    @Override
    public List<Capsule> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        eav.put(":topicCode", new AttributeValue().withS(topicCode));
        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression(STATUS_KEY+" = :status and topicCode = :topicCode)")
                .withExpressionAttributeValues(eav);

        return dynamo.query(Capsule.class, queryExpression);

    }

    private List<Capsule> queryCapsules (QueryCriteria queryCriteria){

        Map<String,String> expressionAttributesNames = new HashMap<>();
        String hashKeyName="#".concat(queryCriteria.getHashKeyName());
        String rangeKeyName="#".concat(queryCriteria.getRangeKeyName());
        String hashKeyValue=":".concat(queryCriteria.getHashKeyName());
        String rangeKeyValue=":".concat(queryCriteria.getRangeKeyName());
        String queryExpression= hashKeyName.concat(" = ").concat(hashKeyValue).concat(" and ").concat(rangeKeyName).concat(" = ").concat(rangeKeyValue);

        expressionAttributesNames.put(hashKeyName,queryCriteria.getHashKeyName());
        expressionAttributesNames.put(rangeKeyName,queryCriteria.getRangeKeyValue());

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(hashKeyValue,new AttributeValue().withS(queryCriteria.getHashKeyValue()));
        expressionAttributeValues.put(rangeKeyValue,new AttributeValue().withS(queryCriteria.getRangeKeyValue()));

        DynamoDBQueryExpression<Capsule> dynamoDBQueryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName(queryCriteria.getIndexName())
                .withKeyConditionExpression(queryExpression)
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        log.info(String.format("query expression: %s",dynamoDBQueryExpression.toString()));

        return dynamo.query(Capsule.class,dynamoDBQueryExpression);
    }

}
