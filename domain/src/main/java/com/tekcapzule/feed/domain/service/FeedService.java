package com.tekcapzule.feed.domain.service;

import com.tekcapzule.feed.domain.command.*;
import com.tekcapzule.feed.domain.model.Feed;

import java.util.List;
import java.util.Map;


public interface FeedService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<Feed> getMyFeed(List<String> subscribedTopics);

    List<Feed> getPendingApproval();

    void approve(ApproveCommand approveCommand);

    void view(ViewCommand viewCommand);

    void addBookMark(AddBookmarkCommand addBookmarkCommand);

    void recommend(RecommendCommand recommendCommand);

    Feed findBy( String feedId);

    List<Feed> findByTopic( String topicName);
    Map<String,List<String>> getMetadata();

    List<Feed> findAll();
    int getAllFeedsCount();
}
