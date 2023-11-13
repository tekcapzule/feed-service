package com.tekcapzule.feed.application.function;

import com.tekcapzule.feed.application.config.AppConfig;
import com.tekcapzule.feed.application.function.input.ApproveFeedInput;
import com.tekcapzule.feed.application.mapper.InputOutputMapper;
import com.tekcapzule.feed.domain.command.ApproveCommand;
import com.tekcapzule.feed.domain.service.FeedService;
import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class ApproveFunction implements Function<Message<ApproveFeedInput>, Message<Void>> {

    private final FeedService feedService;

    private final AppConfig appConfig;

    public ApproveFunction(final FeedService feedService, final AppConfig appConfig) {
        this.feedService = feedService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<ApproveFeedInput> approveFeedInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            ApproveFeedInput approveFeedInput = approveFeedInputMessage.getPayload();
            log.info(String.format("Entering approve feed Function -  Feed Id:%s", approveFeedInput.getFeedId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(approveFeedInputMessage.getHeaders());
            ApproveCommand approveCommand = InputOutputMapper.buildApproveFeedCommandFromApproveFeedInput.apply(approveFeedInput, origin);
            feedService.approve(approveCommand);
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