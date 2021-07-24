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
public class Contact implements ValueObject {
    @DynamoDBAttribute(attributeName = "mailAddress")
    private Address mailAddress;
    @DynamoDBAttribute(attributeName = "emailId")
    private String emailId;
    @DynamoDBAttribute(attributeName = "primaryContactNumber")
    private String primaryContactNumber;
    @DynamoDBAttribute(attributeName = "secondaryContactNumber")
    private String secondaryContactNumber;
    @DynamoDBAttribute(attributeName = "timeZone")
    private String timeZone;
    @DynamoDBAttribute(attributeName = "websiteUrl")
    private String websiteUrl;
}
