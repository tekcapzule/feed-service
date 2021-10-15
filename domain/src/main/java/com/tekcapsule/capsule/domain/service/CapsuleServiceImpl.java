package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CapsuleServiceImpl implements CapsuleService {
    private CapsuleDynamoRepository capsuleDynamoRepository;

    @Autowired
    public CapsuleServiceImpl(CapsuleDynamoRepository capsuleDynamoRepository) {
        this.capsuleDynamoRepository = capsuleDynamoRepository;
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

        return capsuleDynamoRepository.save(capsule);
    }

    @Override
    public Capsule update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Capsule capsule = capsuleDynamoRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (capsule != null) {
            capsule.setAwards(updateCommand.getAwards());
            capsule.setHeadLine(updateCommand.getHeadLine());

            capsule.setUpdatedOn(updateCommand.getExecOn());
            capsule.setUpdatedBy(updateCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
        return capsule;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        capsuleDynamoRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public Capsule get( String capsuleId) {

        log.info(String.format("Entering get capsule service - Capsule Id:{0}", capsuleId));

        return capsuleDynamoRepository.findBy( capsuleId);
    }
}
