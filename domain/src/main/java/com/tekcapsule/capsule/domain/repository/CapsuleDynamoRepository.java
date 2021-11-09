package com.tekcapsule.capsule.domain.repository;

import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.core.domain.CrudRepository;

import java.util.List;


public interface CapsuleDynamoRepository extends CrudRepository<Capsule, String> {
    
    Capsule findBy(String hashKey, String rangeKey);

    List<Capsule> findAllFeeds (List<String> subscribedTopics);

    List<Capsule> findAllEditorsPick();

    List<Capsule> findAllTrending();

    List<Capsule> findAllPendingApproval();

    List<Capsule> findAllByTopicCode(String topicCode);
}
