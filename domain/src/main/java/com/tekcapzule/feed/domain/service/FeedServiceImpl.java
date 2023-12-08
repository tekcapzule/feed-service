package com.tekcapzule.feed.domain.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tekcapzule.feed.domain.command.*;
import com.tekcapzule.feed.domain.exception.FeedCreationException;
import com.tekcapzule.feed.domain.exception.S3ClientException;
import com.tekcapzule.feed.domain.model.*;
import com.tekcapzule.feed.domain.repository.FeedDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedServiceImpl implements FeedService {
    private FeedDynamoRepository feedDynamoRepository;

    @Autowired
    public FeedServiceImpl(FeedDynamoRepository feedDynamoRepository) {
        this.feedDynamoRepository = feedDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create feed service - Feed Title:%s", createCommand.getTitle()));

        Feed feed = Feed.builder()
                .audience(createCommand.getAudience())
                .author(createCommand.getAuthor())
                .description(createCommand.getDescription())
                .topicCode(createCommand.getTopicCode())
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
                .keyPoints(createCommand.getKeyPoints())
                .build();

        feed.setAddedOn(createCommand.getExecOn());
        feed.setUpdatedOn(createCommand.getExecOn());
        feed.setAddedBy(createCommand.getExecBy().getUserId());

        feedDynamoRepository.save(feed);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update feed service - Feed Id:%s", updateCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(updateCommand.getFeedId());
        if (feed != null) {

            feed.setAudience(updateCommand.getAudience());
            feed.setAuthor(updateCommand.getAuthor());
            feed.setDescription(updateCommand.getDescription());
            feed.setTopicCode(updateCommand.getTopicCode());
            feed.setPublishedDate(updateCommand.getPublishedDate());
            feed.setTitle(updateCommand.getTitle());
            feed.setImageUrl(updateCommand.getImageUrl());
            feed.setDuration(updateCommand.getDuration());
            feed.setAuthor(updateCommand.getAuthor());
            feed.setTags(updateCommand.getTags());
            feed.setPublisher(updateCommand.getPublisher());
            feed.setResourceUrl(updateCommand.getResourceUrl());
            feed.setType(updateCommand.getType());
            feed.setAudience(updateCommand.getAudience());
            feed.setExpiryDate(updateCommand.getExpiryDate());
            feed.setLevel(updateCommand.getLevel());
            feed.setKeyPoints(updateCommand.getKeyPoints());
            feed.setUpdatedOn(updateCommand.getExecOn());
            feed.setUpdatedBy(updateCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable feed service -  Feed Id:%s", disableCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(disableCommand.getFeedId());
        if (feed != null) {
            feed.setStatus(Status.INACTIVE);

            feed.setUpdatedOn(disableCommand.getExecOn());
            feed.setUpdatedBy(disableCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }

    @Override
    public List<Feed> getMyFeed(List<String> subscribedTopics) {
        log.info("Entering getMyFeed service");

        return feedDynamoRepository.findAllFeeds(subscribedTopics);
    }

    @Override
    public List<Feed> getPendingApproval() {
        log.info("Entering getPendingApproval service");

        return feedDynamoRepository.findAllPendingApproval();
    }

    @Override
    public Map<String, List<String>> getMetadata() {
        log.info("Entering getMetadata service");
        Map<String, List<String>> metadata = new HashMap<>();
        metadata.put("feedType", Arrays.stream(FeedType.values()).map(f -> f.toString()).collect(Collectors.toList()));
        metadata.put("publisher", Arrays.stream(Publisher.values()).map(f -> f.toString()).collect(Collectors.toList()));
        metadata.put("topicLevel", Arrays.stream(TopicLevel.values()).map(f -> f.toString()).collect(Collectors.toList()));
        metadata.put("targetAudience", Arrays.stream(TargetAudience.values()).map(f -> f.toString()).collect(Collectors.toList()));
        metadata.put("expiryInterval", Arrays.stream(ExpiryInterval.values()).map(f -> f.toString()).collect(Collectors.toList()));
        return metadata;
    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve feed service -  Feed Id:%s", approveCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(approveCommand.getFeedId());
        if (feed != null) {
            feed.setStatus(Status.ACTIVE);

            feed.setUpdatedOn(approveCommand.getExecOn());
            feed.setUpdatedBy(approveCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }

    @Override
    public void view(ViewCommand viewCommand) {
        log.info(String.format("Entering view feed service -  Feed Id:%s", viewCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(viewCommand.getFeedId());
        if (feed != null) {
            Integer views = feed.getViews();
            views += 1;
            feed.setViews(views);
            feed.setUpdatedOn(viewCommand.getExecOn());
            feed.setUpdatedBy(viewCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }

    @Override
    public void addBookMark(AddBookmarkCommand addBookmarkCommand) {
        log.info(String.format("Entering addBookmark feed service -  Feed Id:%s", addBookmarkCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(addBookmarkCommand.getFeedId());

        if (feed != null) {
            Integer bookmarkCount = feed.getBookmarks();
            bookmarkCount += 1;
            feed.setBookmarks(bookmarkCount);

            feed.setUpdatedOn(addBookmarkCommand.getExecOn());
            feed.setUpdatedBy(addBookmarkCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend feed service -  Feed Id:%s", recommendCommand.getFeedId()));

        Feed feed = feedDynamoRepository.findBy(recommendCommand.getFeedId());
        if (feed != null) {
            Integer recommendationsCount = feed.getRecommendations();
            recommendationsCount += 1;
            feed.setRecommendations(recommendationsCount);

            feed.setUpdatedOn(recommendCommand.getExecOn());
            feed.setUpdatedBy(recommendCommand.getExecBy().getUserId());

            feedDynamoRepository.save(feed);
        }
    }


    @Override
    public Feed findBy(String feedId) {

        log.info(String.format("Entering find by feed service - Feed Id:%s", feedId));

        return feedDynamoRepository.findBy(feedId);
    }

    @Override
    public List<Feed> findByTopic(String topicCode) {
        log.info("Entering findBy topic service");

        return feedDynamoRepository.findAllByTopicCode(topicCode);
    }


    @Override
    public List<Feed> findAll() {

        log.info("Entering findAll feed service");

        return feedDynamoRepository.findAll();
    }

    @Override
    public int getAllFeedsCount() {
        log.info("Entering get all feeds count service");
        return feedDynamoRepository.getAllFeedsCount();
    }
}
