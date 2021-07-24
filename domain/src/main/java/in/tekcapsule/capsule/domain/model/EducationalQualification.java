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
public class EducationalQualification implements ValueObject {
    @DynamoDBAttribute(attributeName = "school")
    private String school;
    @DynamoDBAttribute(attributeName = "degree")
    private String degree;
    @DynamoDBAttribute(attributeName = "field")
    private String field;
    @DynamoDBAttribute(attributeName = "startDate")
    private String startDate;
    @DynamoDBAttribute(attributeName = "endDate")
    private String endDate;
}
