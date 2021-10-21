package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.model.*;
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

        log.info(String.format("Entering create capsule service - Capsule Title:{0}", createCommand.getTitle()));

        Capsule capsule = Capsule.builder()
                .audience(createCommand.getAudience())
                .author(createCommand.getAuthor())
                .description(createCommand.getDescription())
                .topicName(createCommand.getTopicName())
                .editorsPick(createCommand.isEditorsPick())
                .imageUrl(createCommand.getImageUrl())
                .duration(createCommand.getDuration())
                .publishedDate(createCommand.getPublishedDate())
                .expiryDate(createCommand.getExpiryDate())
                .publisher(createCommand.getPublisher())
                .resourceUrl(createCommand.getResourceUrl())
                .level(createCommand.getLevel())
                .status(Status.SUBMITTED)
                .tags(createCommand.getTags())
                .title(createCommand.getTitle())
                .type(createCommand.getType())
                .views(0)
                .recommendations(0)
                .bookmarks(0)
                .active(true)
                .build();

        capsule.setAddedOn(createCommand.getExecOn());
        capsule.setUpdatedOn(createCommand.getExecOn());
        capsule.setAddedBy(createCommand.getExecBy().getUserId());

        return capsuleDynamoRepository.save(capsule);
    }

    @Override
    public Capsule update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update capsule service - Capsule Title:{0}", updateCommand.getTitle()));

        Capsule capsule = capsuleDynamoRepository.findBy(updateCommand.getTitle());
        if (capsule != null) {
            capsule.setActive(true);

            capsule.setAudience(updateCommand.getAudience());
            capsule.setAuthor(updateCommand.getAuthor());
            capsule.setDescription(updateCommand.getDescription());
            capsule.setTopicName(updateCommand.getTopicName());
            capsule.setPublishedDate(updateCommand.getPublishedDate());
            capsule.setTitle(updateCommand.getTitle());
            capsule.setImageUrl(updateCommand.getImageUrl());
            capsule.setDuration(updateCommand.getDuration());
            capsule.setAuthor(updateCommand.getAuthor());
            capsule.setTags(updateCommand.getTags());
            capsule.setPublisher(updateCommand.getPublisher());
            capsule.setResourceUrl(updateCommand.getResourceUrl());
            capsule.setType(updateCommand.getType());
            capsule.setAudience(updateCommand.getAudience());
            capsule.setExpiryDate(updateCommand.getExpiryDate());
            capsule.setLevel(updateCommand.getLevel());

            capsule.setUpdatedOn(updateCommand.getExecOn());
            capsule.setUpdatedBy(updateCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
        return capsule;

    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable capsule service -  Capsule Title:{0}", disableCommand.getTopicName()));

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
