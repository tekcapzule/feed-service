package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.*;
import com.tekcapsule.capsule.domain.model.*;
import com.tekcapsule.capsule.domain.repository.CapsuleDynamoRepository;
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
                .topicCode(createCommand.getTopicCode())
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
                .build();

        capsule.setAddedOn(createCommand.getExecOn());
        capsule.setUpdatedOn(createCommand.getExecOn());
        capsule.setAddedBy(createCommand.getExecBy().getUserId());

        return capsuleDynamoRepository.save(capsule);
    }

    @Override
    public Capsule update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update capsule service - Capsule Id:{0}", updateCommand.getCapsuleId()));

        Capsule capsule = capsuleDynamoRepository.findBy(updateCommand.getCapsuleId());
        if (capsule != null) {

            capsule.setAudience(updateCommand.getAudience());
            capsule.setAuthor(updateCommand.getAuthor());
            capsule.setDescription(updateCommand.getDescription());
            capsule.setTopicCode(updateCommand.getTopicCode());
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

        log.info(String.format("Entering disable capsule service -  Capsule Id:{0}", disableCommand.getCapsuleId()));

        Capsule capsule = capsuleDynamoRepository.findBy(disableCommand.getCapsuleId());
        if (capsule != null) {
            capsule.setStatus(Status.INACTIVE);

            capsule.setUpdatedOn(disableCommand.getExecOn());
            capsule.setUpdatedBy(disableCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
    }

    @Override
    public List<Capsule> getMyFeed(List<String> subscribedTopics) {
        log.info(String.format("Entering getMyFeed service"));

        return capsuleDynamoRepository.findAllFeeds(subscribedTopics);
    }

    @Override
    public List<Capsule> getTrending() {
        log.info(String.format("Entering getTrending service"));

        return capsuleDynamoRepository.findAllTrending();
    }

    @Override
    public List<Capsule> getEditorsPick() {
        log.info(String.format("Entering getEditorsPick service"));

        return capsuleDynamoRepository.findAllEditorsPick();
    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve capsule service -  Capsule Id:{0}", approveCommand.getCapsuleId()));

        Capsule capsule = capsuleDynamoRepository.findBy(approveCommand.getCapsuleId());
        if (capsule != null) {
            capsule.setStatus(Status.ACTIVE);

            capsule.setUpdatedOn(approveCommand.getExecOn());
            capsule.setUpdatedBy(approveCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
    }

    @Override
    public void addBookMark(AddBookmarkCommand addBookmarkCommand) {
        log.info(String.format("Entering addBookmark capsule service -  Capsule Id:{0}", addBookmarkCommand.getCapsuleId()));

        Capsule capsule = capsuleDynamoRepository.findBy(addBookmarkCommand.getCapsuleId());

        if (capsule != null) {
            Integer bookmarkCount = capsule.getBookmarks();
            bookmarkCount+=1;
            capsule.setBookmarks(bookmarkCount);

            capsule.setUpdatedOn(addBookmarkCommand.getExecOn());
            capsule.setUpdatedBy(addBookmarkCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend capsule service -  Capsule Id:{0}", recommendCommand.getCapsuleId()));

        Capsule capsule = capsuleDynamoRepository.findBy(recommendCommand.getCapsuleId());
        if (capsule != null) {
            Integer recommendationsCount = capsule.getRecommendations();
            recommendationsCount+=1;
            capsule.setBookmarks(recommendationsCount);

            capsule.setUpdatedOn(recommendCommand.getExecOn());
            capsule.setUpdatedBy(recommendCommand.getExecBy().getUserId());

            capsuleDynamoRepository.save(capsule);
        }
    }


    @Override
    public Capsule findBy( String capsuleId) {

        log.info(String.format("Entering find by capsule service - Capsule Id:{0}", capsuleId));

        return capsuleDynamoRepository.findBy( capsuleId);
    }

    @Override
    public List<Capsule> findByTopic(String topicCode) {
        log.info(String.format("Entering findBy topic service"));

        return capsuleDynamoRepository.findAllByTopicCode( topicCode);
    }
}
