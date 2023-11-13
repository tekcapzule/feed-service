package com.tekcapzule.feed.application.function;

import com.tekcapzule.feed.application.config.AppConfig;
import com.tekcapzule.feed.application.function.input.SearchByTopicInput;
import com.tekcapzule.feed.domain.model.Feed;
import com.tekcapzule.feed.domain.service.FeedService;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class SearchByTopicFunction implements Function<Message<SearchByTopicInput>, Message<List<Feed>>> {

    private final FeedService feedService;

    private final AppConfig appConfig;

    public SearchByTopicFunction(final FeedService feedService, final AppConfig appConfig) {
        this.feedService = feedService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<List<Feed>> apply(Message<SearchByTopicInput> findByTopicInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        List<Feed> feeds =new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            SearchByTopicInput searchByTopicInput = findByTopicInputMessage.getPayload();
            log.info(String.format("Entering search by topic Function topics %s", searchByTopicInput.getSubscribedTopic()));
            feeds = feedService.findByTopic(searchByTopicInput.getSubscribedTopic());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(feeds, responseHeaders);
    }
}