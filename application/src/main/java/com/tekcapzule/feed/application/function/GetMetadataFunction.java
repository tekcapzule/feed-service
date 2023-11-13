package com.tekcapzule.feed.application.function;

import com.tekcapzule.feed.application.config.AppConfig;
import com.tekcapzule.feed.domain.service.FeedService;
import com.tekcapzule.core.domain.EmptyFunctionInput;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetMetadataFunction implements Function<Message<EmptyFunctionInput>, Message<Map<String,List<String>>>> {

    private final FeedService feedService;

    private final AppConfig appConfig;

    public GetMetadataFunction(final FeedService feedService, final AppConfig appConfig) {
        this.feedService = feedService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Map<String,List<String>>> apply(Message<EmptyFunctionInput> message) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String,List<String>> metaData = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info(String.format("Entering get Feed Metadata Function"));
            metaData = feedService.getMetadata();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(metaData, responseHeaders);
    }
}