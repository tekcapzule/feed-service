package com.tekcapzule.feed.domain.repository;

import com.tekcapzule.feed.domain.model.Feed;
import com.tekcapzule.core.domain.CrudRepository;

import java.util.List;


public interface FeedDynamoRepository extends CrudRepository<Feed, String> {
    
    Feed findBy(String hashKey, String rangeKey);

    List<Feed> findAllFeeds (List<String> subscribedTopics);

    List<Feed> findAllPendingApproval();

    List<Feed> findAllByTopicCode(String topicCode);

    int getAllFeedsCount();


}
