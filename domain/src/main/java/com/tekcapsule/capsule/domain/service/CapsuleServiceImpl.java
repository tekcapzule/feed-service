package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import lombok.extern.slf4j.Slf4j;
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

        log.info(String.format("Entering create capsule service - Capsule Name:{0}", createCommand.getTitle()));

        Capsule capsule = Capsule.builder()
                .active(true)
                .build();

        capsule.setAddedOn(createCommand.getExecOn());
        capsule.setUpdatedOn(createCommand.getExecOn());
        capsule.setAddedBy(createCommand.getExecBy().getUserId());

        return capsuleDynamoRepository.save(capsule);
    }

    @Override
    public Capsule update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update capsule service - Capsule Id:{0}", updateCommand.getTitle()));

        Capsule capsule = capsuleDynamoRepository.findBy(updateCommand.getTitle());
        if (capsule != null) {
            capsule.setActive(true);

            capsule.setUpdatedOn(updateCommand.getExecOn());
            capsule.setUpdatedBy(updateCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
        return capsule;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable capsule service -  Capsule Id:{1}", disableCommand.getTopicName()));

        capsuleDynamoRepository.disable(disableCommand.getTopicName());
    }

    @Override
    public Capsule get( String capsuleId) {

        log.info(String.format("Entering get capsule service - Capsule Id:{0}", capsuleId));

        return capsuleDynamoRepository.findBy( capsuleId);
    }
}
