package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.*;
import com.tekcapsule.capsule.domain.model.Capsule;

import java.util.List;
import java.util.Map;


public interface CapsuleService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Capsule> getMyFeed(List<String> subscribedTopics);

    List<Capsule> getTrending();

    List<Capsule> getPendingApproval();

    List<Capsule> getEditorsPick();

    void approve(ApproveCommand approveCommand);

    void view(ViewCommand viewCommand);

    void addBookMark(AddBookmarkCommand addBookmarkCommand);

    void recommend(RecommendCommand recommendCommand);

    Capsule findBy( String capsuleId);

    List<Capsule> findByTopic( String topicName);
    Map<String,List<String>> getMetadata();
    List<Capsule> findByCategory(String topicCode, String category);
    List<Capsule> findBySubCategory(String topicCode, String category, String subCategory);
    List<Capsule> findAll();
    int getAllCapsulesCount();
}
