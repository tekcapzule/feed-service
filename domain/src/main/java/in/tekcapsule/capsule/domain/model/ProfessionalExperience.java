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
public class ProfessionalExperience implements ValueObject {
    @DynamoDBAttribute(attributeName = "jobTitle")
    private String jobTitle;
    @DynamoDBAttribute(attributeName = "company")
    private String company;
    @DynamoDBAttribute(attributeName = "location")
    private String location;
    @DynamoDBAttribute(attributeName = "startDate")
    private String startDate;
    @DynamoDBAttribute(attributeName = "endDate")
    private String endDate;
    @DynamoDBAttribute(attributeName = "currentlyWorking")
    private Boolean currentlyWorking;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
}
