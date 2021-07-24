package in.tekcapsule.capsule.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.devstream.core.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBDocument
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Social implements ValueObject {
    @DynamoDBAttribute(attributeName = "linkedInUrl")
    private String linkedInUrl;
    @DynamoDBAttribute(attributeName = "twitterHandle")
    private String twitterHandle;
    @DynamoDBAttribute(attributeName = "blogUrl")
    private String blogUrl;
}