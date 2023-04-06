package com.tekcapsule.capsule.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.capsule.domain.model.Capsule;
import lombok.extern.slf4j.Slf4j;
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
    public static final String SUBMITTED_STATUS = "SUBMITTED";
    public static final String STATUS_KEY = "status";


    @Autowired
    public CapsuleRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Capsule> findAll() {

        return dynamo.scan(Capsule.class, new DynamoDBScanExpression());

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

            HashMap<String, AttributeValue> expAttributes = new HashMap<>();
            expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
            expAttributes.put(":topicCode", new AttributeValue().withS(topic));

            HashMap<String, String> expNames = new HashMap<>();
            expNames.put("#status", "status");
            expNames.put("#topicCode", "topicCode");

            DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                    .withIndexName("topicGSI").withConsistentRead(false)
                    .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                    .withExpressionAttributeValues(expAttributes)
                    .withExpressionAttributeNames(expNames);
            PaginatedQueryList<Capsule> myCapsules = dynamo.query(Capsule.class, queryExpression);

            if (myCapsules != null && myCapsules.size() > 0) {
                capsules.addAll(myCapsules);
            }
        });
        return capsules;
    }

    @Override
    public List<Capsule> findAllEditorsPick() {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":editorsPick", new AttributeValue().withN("1"));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#editorsPick", "editorsPick");

        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("editorsPickGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #editorsPick = :editorsPick")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);
        return dynamo.query(Capsule.class, queryExpression);

    }

    @Override
    public List<Capsule> findAllTrending() {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":views", new AttributeValue().withN("25"));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#views", "views");

        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("trendingsGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #views > :views")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Capsule.class, queryExpression);

    }

    //Find all capsules by topic code.
    @Override
    public List<Capsule> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#topicCode", "topicCode");

        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Capsule.class, queryExpression);

    }

    @Override
    public List<Capsule> findAllPendingApproval() {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(SUBMITTED_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("trendingsGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Capsule.class, queryExpression);

    }

    @Override
    public List<Capsule> findAllByCategory(String topicCode, String category) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));
        expAttributes.put(":category", new AttributeValue().withS(category));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#topicCode", "topicCode");
        expNames.put("#categoryCode", "categoryCode");

        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("categoryGSI").withConsistentRead(false)
                .withKeyConditionExpression("#topicCode = :topicCode and begins_with(#categoryCode, :category)")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Capsule.class, queryExpression);
    }

    @Override
    public List<Capsule> findAllBySubCategory(String topicCode, String category, String subCategory) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":categoryCode", new AttributeValue().withS(category+"-"+subCategory));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#topicCode", "topicCode");
        expNames.put("#categoryCode", "categoryCode");


        DynamoDBQueryExpression<Capsule> queryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName("categoryGSI").withConsistentRead(false)
                .withKeyConditionExpression("#topicCode = :topicCode and #categoryCode = :categoryCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);
        return dynamo.query(Capsule.class, queryExpression);

    }

    private List<Capsule> queryCapsules(QueryCriteria queryCriteria) {

        Map<String, String> expressionAttributesNames = new HashMap<>();
        String hashKeyName = "#" .concat(queryCriteria.getHashKeyName());
        String rangeKeyName = "#" .concat(queryCriteria.getRangeKeyName());
        String hashKeyValue = ":" .concat(queryCriteria.getHashKeyName());
        String rangeKeyValue = ":" .concat(queryCriteria.getRangeKeyName());
        String queryExpression = hashKeyName.concat(" = ").concat(hashKeyValue).concat(" and ").concat(rangeKeyName).concat(" = ").concat(rangeKeyValue);

        expressionAttributesNames.put(hashKeyName, queryCriteria.getHashKeyName());
        expressionAttributesNames.put(rangeKeyName, queryCriteria.getRangeKeyValue());

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(hashKeyValue, new AttributeValue().withS(queryCriteria.getHashKeyValue()));
        expressionAttributeValues.put(rangeKeyValue, new AttributeValue().withS(queryCriteria.getRangeKeyValue()));

        DynamoDBQueryExpression<Capsule> dynamoDBQueryExpression = new DynamoDBQueryExpression<Capsule>()
                .withIndexName(queryCriteria.getIndexName())
                .withKeyConditionExpression(queryExpression)
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        log.info(String.format("query expression: %s", dynamoDBQueryExpression.toString()));

        return dynamo.query(Capsule.class, dynamoDBQueryExpression);
    }



}
