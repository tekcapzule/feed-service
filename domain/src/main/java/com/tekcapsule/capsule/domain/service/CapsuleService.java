package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.*;
import com.tekcapsule.capsule.domain.model.Capsule;

import java.util.List;


public interface CapsuleService {

    Capsule create(CreateCommand createCommand);

    Capsule update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Capsule> getMyFeed(List<String> subscribedTopics);

    List<Capsule> getTrending();

    List<Capsule> getEditorsPick();

    void approve(ApproveCommand approveCommand);

    void addBookMark(AddBookmarkCommand addBookmarkCommand);

    void recommend(RecommendCommand recommendCommand);

    Capsule findBy( String capsuleId);

    List<Capsule> findByTopic( List<String> subscribedTopics);
}
