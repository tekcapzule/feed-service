package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        log.info(String.format("Entering update capsule service - Capsule Name:{0}", updateCommand.getTitle()));

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

        log.info(String.format("Entering disable capsule service -  Capsule Name:{0}", disableCommand.getTopicName()));

        Capsule capsule = capsuleDynamoRepository.findBy(disableCommand.getTopicName(),disableCommand.getPublishedDate());
        if (capsule != null) {
            capsule.setActive(false);

            capsule.setUpdatedOn(disableCommand.getExecOn());
            capsule.setUpdatedBy(disableCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
    }

    @Override
    public List<Capsule> getMyFeed(List<String> subscribedTopics) {
        return null;
    }

    @Override
    public List<Capsule> getTrending() {
        return null;
    }

    @Override
    public List<Capsule> getEditorsPick() {
        return null;
    }

    @Override
    public Void approve(String topicName, String publishedDate) {
        return null;
    }


    @Override
    public Capsule findBy( String topicName, String publishedDate) {

        log.info(String.format("Entering find by capsule service - Topic Name:{0}", topicName, publishedDate));

        return capsuleDynamoRepository.findBy( topicName,publishedDate);
    }
}
