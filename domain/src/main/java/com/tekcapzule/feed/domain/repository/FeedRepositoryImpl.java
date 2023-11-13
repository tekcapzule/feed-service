package com.tekcapzule.feed.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.tekcapzule.feed.domain.model.Feed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class FeedRepositoryImpl implements FeedDynamoRepository {

    private DynamoDBMapper dynamo;

    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String SUBMITTED_STATUS = "SUBMITTED";
    public static final String STATUS_KEY = "status";


    @Autowired
    public FeedRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Feed> findAll() {
        String projectionExpression = "feedId,title,topicCode,category,#status";
        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        return dynamo.scan(Feed.class,new DynamoDBScanExpression().withProjectionExpression(projectionExpression).withExpressionAttributeNames(expNames));
    }

    @Override
    public int getAllFeedsCount() {
        return dynamo.count(Feed.class,new DynamoDBScanExpression().withSelect(Select.COUNT));
    }

    @Override
    public Feed findBy(String feedId) {
        return dynamo.load(Feed.class, feedId);
    }

    @Override
    public Feed save(Feed feed) {
        dynamo.save(feed);
        return feed;
    }

    @Override
    public Feed findBy(String hashKey, String rangeKey) {
        return dynamo.load(Feed.class, hashKey, rangeKey);
    }

    @Override
    public List<Feed> findAllFeeds(List<String> subscribedTopics) {

        List<Feed> feeds = new ArrayList<>();

        subscribedTopics.forEach(topic -> {

            HashMap<String, AttributeValue> expAttributes = new HashMap<>();
            expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
            expAttributes.put(":topicCode", new AttributeValue().withS(topic));

            HashMap<String, String> expNames = new HashMap<>();
            expNames.put("#status", "status");
            expNames.put("#topicCode", "topicCode");

            DynamoDBQueryExpression<Feed> queryExpression = new DynamoDBQueryExpression<Feed>()
                    .withIndexName("topicGSI").withConsistentRead(false)
                    .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                    .withExpressionAttributeValues(expAttributes)
                    .withExpressionAttributeNames(expNames);
            PaginatedQueryList<Feed> myFeeds = dynamo.query(Feed.class, queryExpression);

            if (myFeeds != null && myFeeds.size() > 0) {
                feeds.addAll(myFeeds);
            }
        });
        return feeds;
    }
    //Find all feeds by topic code.
    @Override
    public List<Feed> findAllByTopicCode(String topicCode) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");
        expNames.put("#topicCode", "topicCode");

        DynamoDBQueryExpression<Feed> queryExpression = new DynamoDBQueryExpression<Feed>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Feed.class, queryExpression);

    }

    @Override
    public List<Feed> findAllPendingApproval() {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(SUBMITTED_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<Feed> queryExpression = new DynamoDBQueryExpression<Feed>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Feed.class, queryExpression);

    }

    private List<Feed> queryFeeds(QueryCriteria queryCriteria) {

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

        DynamoDBQueryExpression<Feed> dynamoDBQueryExpression = new DynamoDBQueryExpression<Feed>()
                .withIndexName(queryCriteria.getIndexName())
                .withKeyConditionExpression(queryExpression)
                .withExpressionAttributeNames(expressionAttributesNames)
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        log.info(String.format("query expression: %s", dynamoDBQueryExpression.toString()));

        return dynamo.query(Feed.class, dynamoDBQueryExpression);
    }



}
