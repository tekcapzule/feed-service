package com.tekcapsule.capsule.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.devstream.core.domain.AggregateRoot;
import in.devstream.core.domain.BaseDomainEntity;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "Capsule")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Capsule extends BaseDomainEntity<String> implements AggregateRoot {

    @DynamoDBHashKey(attributeName="topicName")
    private String topicName;
    @DynamoDBRangeKey(attributeName="publishedDate")
    private String publishedDate;
    @DynamoDBAttribute(attributeName = "title")
    private String title;
    @DynamoDBAttribute(attributeName = "imageUrl")
    private String imageUrl;
    @DynamoDBAttribute(attributeName = "duration")
    private Integer duration;
    @DynamoDBAttribute(attributeName = "author")
    private String author;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName="tags")
    private List<String> tags;
    @DynamoDBAttribute(attributeName = "publisher")
    private String publisher;
    @DynamoDBAttribute(attributeName = "resourceUrl")
    private String resourceUrl;
    @DynamoDBAttribute(attributeName = "type")
    private CapsuleType type;
    @DynamoDBAttribute(attributeName = "audience")
    private TargetAudience audience;
    @DynamoDBAttribute(attributeName = "level")
    private TopicLevel level;
    @DynamoDBAttribute(attributeName = "expiryDate")
    private String expiryDate;
    @DynamoDBAttribute(attributeName = "editorsPick")
    private boolean editorsPick;
    @DynamoDBAttribute(attributeName = "views")
    private Integer views;
    @DynamoDBAttribute(attributeName = "bookmarks")
    private Integer bookmarks;
    @DynamoDBAttribute(attributeName = "recommendations")
    private Integer recommendations;
    @DynamoDBAttribute(attributeName = "status")
    private Status status;
    @DynamoDBAttribute(attributeName = "active")
    private boolean active;
}