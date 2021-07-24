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
public class Name implements ValueObject {
    @DynamoDBAttribute(attributeName = "salutation")
    private String salutation;
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "middleName")
    private String middleName;
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;
    @DynamoDBAttribute(attributeName = "displayName")
    private String displayName;
}
