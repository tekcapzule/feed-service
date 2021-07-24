package in.tekcapsule.capsule.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String tenantId;
    private Name name;
    private Gender gender;
    private String headLine;
    private Contact contact;
    private String photoUrl;
    private DateOfBirth dateOfBirth;
    private List<String> tags;
    List<ProfessionalExperience> professionalExperiences;
    List<EducationalQualification> educationalQualifications;
    List<Award> awards;
    List<Certification> certifications;
    List<Publication> publications;
    private Social social;
}
