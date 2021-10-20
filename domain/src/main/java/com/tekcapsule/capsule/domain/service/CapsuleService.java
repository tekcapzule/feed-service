package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import com.tekcapsule.capsule.domain.model.Capsule;

import java.util.List;


public interface CapsuleService {

    Capsule create(CreateCommand createCommand);

    Capsule update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Capsule> getMyFeed(List<String> subscribedTopics);

    List<Capsule> getTrending();

    List<Capsule> getEditorsPick();

    Void approve( String topicName, String publishedDate);
    Capsule findBy( String topicName, String publishedDate);
}
