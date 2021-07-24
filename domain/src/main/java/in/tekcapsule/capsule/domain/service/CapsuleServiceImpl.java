package in.tekcapsule.capsule.domain.service;

import in.tekcapsule.capsule.domain.command.CreateCommand;
import in.tekcapsule.capsule.domain.command.DisableCommand;
import in.tekcapsule.capsule.domain.command.UpdateCommand;
import in.tekcapsule.capsule.domain.model.DateOfBirth;
import in.tekcapsule.capsule.domain.model.Mentor;
import in.tekcapsule.capsule.domain.model.Name;
import in.tekcapsule.capsule.domain.query.SearchItem;
import in.tekcapsule.capsule.domain.query.SearchQuery;
import in.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CapsuleServiceImpl implements CapsuleService {
    private CapsuleDynamoRepository mentorRepository;

    @Autowired
    public CapsuleServiceImpl(CapsuleDynamoRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public Mentor create(CreateCommand createCommand) {

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Name name = createCommand.getName();
        if (name != null) {
            name.setDisplayName(String.format("{0} {1}", name.getFirstName(), name.getLastName()));
        }
        DateOfBirth dateOfBirth = createCommand.getDateOfBirth();
        if (dateOfBirth != null) {
            dateOfBirth.setDateOfBirth(String.format("{0}/{1}/{2}", dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear()));
        }
        Mentor mentor = Mentor.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .certifications(createCommand.getCertifications())
                .contact(createCommand.getContact())
                .dateOfBirth(dateOfBirth)
                .educationalQualifications(createCommand.getEducationalQualifications())
                .headLine(createCommand.getHeadLine())
                .name(name)
                .professionalExperiences(createCommand.getProfessionalExperiences())
                .publications(createCommand.getPublications())
                .social(createCommand.getSocial())
                .tags(createCommand.getTags())
                .tenantId(createCommand.getTenantId())
                .userId(createCommand.getContact().getEmailId())
                .build();

        mentor.setAddedOn(createCommand.getExecOn());
        mentor.setUpdatedOn(createCommand.getExecOn());
        mentor.setAddedBy(createCommand.getExecBy().getUserId());

        return mentorRepository.save(mentor);
    }

    @Override
    public Mentor update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Mentor mentor = mentorRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (mentor != null) {
            mentor.setAwards(updateCommand.getAwards());
            mentor.setHeadLine(updateCommand.getHeadLine());
            mentor.setContact(updateCommand.getContact());
            mentor.setCertifications(updateCommand.getCertifications());
            mentor.setPhotoUrl(updateCommand.getPhotoUrl());
            mentor.setTags(updateCommand.getTags());
            mentor.setSocial(updateCommand.getSocial());
            mentor.setEducationalQualifications(updateCommand.getEducationalQualifications());
            mentor.setProfessionalExperiences(updateCommand.getProfessionalExperiences());
            mentor.setPublications(updateCommand.getPublications());

            mentor.setUpdatedOn(updateCommand.getExecOn());
            mentor.setUpdatedBy(updateCommand.getExecBy().getUserId());

            mentorRepository.save(mentor);
        }
        return mentor;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        mentorRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public List<SearchItem> search(SearchQuery searchQuery) {

        log.info(String.format("Entering search mentor service - Tenant Id:{0}", searchQuery.getTenantId()));

        return mentorRepository.search(searchQuery.getTenantId());
    }

    @Override
    public Mentor get(String tenantId, String userId) {

        log.info(String.format("Entering get mentor service - Tenant Id:{0}, User Id:{1}", tenantId, userId));

        return mentorRepository.findBy(tenantId, userId);
    }
}
