package in.tekcapsule.capsule.domain.model;

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
@DynamoDBTable(tableName = "Mentor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mentor extends BaseDomainEntity<String> implements AggregateRoot {

    @DynamoDBHashKey(attributeName="tenantId")
    private String tenantId;
    @DynamoDBRangeKey(attributeName="userId")
    private String userId;

    @DynamoDBAttribute(attributeName = "photoUrl")
    private String photoUrl;
    @DynamoDBAttribute(attributeName = "name")
    private Name name;
    @DynamoDBAttribute(attributeName = "gender")
    private Gender gender;
    @DynamoDBAttribute(attributeName = "headLine")
    private String headLine;
    @DynamoDBAttribute(attributeName = "contact")
    private Contact contact;
    @DynamoDBAttribute(attributeName = "dateOfBirth")
    private DateOfBirth dateOfBirth;
    @DynamoDBAttribute(attributeName = "activeSince")
    private String activeSince;
    @DynamoDBAttribute(attributeName="tags")
    private List<String> tags;
    @DynamoDBAttribute(attributeName="professionalExperiences")
    List<ProfessionalExperience> professionalExperiences;
    @DynamoDBAttribute(attributeName="educationalQualifications")
    List<EducationalQualification> educationalQualifications;
    @DynamoDBAttribute(attributeName="awards")
    List<Award> awards;
    @DynamoDBAttribute(attributeName="certifications")
    List<Certification> certifications;
    @DynamoDBAttribute(attributeName="publications")
    List<Publication> publications;
    @DynamoDBAttribute(attributeName="social")
    private Social social;
    @DynamoDBAttribute(attributeName="rating")
    private int rating;
    @DynamoDBAttribute(attributeName="active")
    private Boolean active;
    @DynamoDBAttribute(attributeName="blocked")
    private boolean blocked;

}