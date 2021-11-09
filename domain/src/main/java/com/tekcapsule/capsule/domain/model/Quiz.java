package com.tekcapsule.capsule.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.ValueObject;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Quiz implements ValueObject {
    private String id;
    private String question;
    private String hint;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private List<String> answerKeys;
}
