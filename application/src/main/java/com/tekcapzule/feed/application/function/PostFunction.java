package com.tekcapzule.feed.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.feed.application.config.AppConfig;
import com.tekcapzule.feed.application.function.input.PostFeedInput;
import com.tekcapzule.feed.application.mapper.InputOutputMapper;
import com.tekcapzule.feed.domain.command.PostCommand;
import com.tekcapzule.feed.domain.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class PostFunction implements Function<Message<PostFeedInput>, Message<Void>> {

    private final FeedService feedService;


    private final AppConfig appConfig;

    public PostFunction(final FeedService feedService, final AppConfig appConfig) {
        this.feedService = feedService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<PostFeedInput> postFeedInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            PostFeedInput postFeedInput = postFeedInputMessage.getPayload();
            log.info(String.format("Entering post feed Function - Feed Source URL : %s", postFeedInput.getFeedSourceUrl()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(postFeedInputMessage.getHeaders());
            PostCommand postCommand = InputOutputMapper.buildPostCommandFromPostFeedInput.apply(postFeedInput, origin);
            feedService.post(postCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}