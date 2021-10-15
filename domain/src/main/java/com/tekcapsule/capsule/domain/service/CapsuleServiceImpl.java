package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import com.tekcapsule.capsule.domain.query.SearchItem;
import com.tekcapsule.capsule.domain.query.SearchQuery;
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
    public Capsule create(CreateCommand createCommand) {

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Capsule capsule = Capsule.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .certifications(createCommand.getCertifications())
                .contact(createCommand.getContact())
                .dateOfBirth(dateOfBirth)
                .build();

        capsule.setAddedOn(createCommand.getExecOn());
        capsule.setUpdatedOn(createCommand.getExecOn());
        capsule.setAddedBy(createCommand.getExecBy().getUserId());

        return mentorRepository.save(capsule);
    }

    @Override
    public Capsule update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Capsule capsule = mentorRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (capsule != null) {
            capsule.setAwards(updateCommand.getAwards());
            capsule.setHeadLine(updateCommand.getHeadLine());

            capsule.setUpdatedOn(updateCommand.getExecOn());
            capsule.setUpdatedBy(updateCommand.getExecBy().getUserId());

            mentorRepository.save(capsule);
        }
        return capsule;
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
    public Capsule get(String tenantId, String userId) {

        log.info(String.format("Entering get mentor service - Tenant Id:{0}, User Id:{1}", tenantId, userId));

        return mentorRepository.findBy(tenantId, userId);
    }
}
