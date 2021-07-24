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
public class Certification implements ValueObject {
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "issuer")
    private String issuer;
    @DynamoDBAttribute(attributeName = "expire")
    private Boolean expire;
    @DynamoDBAttribute(attributeName = "issuedOn")
    private String issuedOn;
    @DynamoDBAttribute(attributeName = "expiresOn")
    private String expiresOn;
    @DynamoDBAttribute(attributeName = "certificateId")
    private String certificateId;
    @DynamoDBAttribute(attributeName = "verificationUrl")
    private String verificationUrl;
}
