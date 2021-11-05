package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConfig;
import com.tekcapsule.capsule.application.function.input.AddBookmarkInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
import com.tekcapsule.capsule.domain.command.AddBookmarkCommand;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class BookmarkFunction implements Function<Message<AddBookmarkInput>, Message<Void>> {

    private final CapsuleService capsuleService;

    private final AppConfig appConfig;

    public BookmarkFunction(final CapsuleService capsuleService, final AppConfig appConfig) {
        this.capsuleService = capsuleService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<AddBookmarkInput> addBookmarkInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            AddBookmarkInput addBookmarkInput = addBookmarkInputMessage.getPayload();
            log.info(String.format("Entering bookmark Function -  Capsule Id:%s", addBookmarkInput.getCapsuleId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(addBookmarkInputMessage.getHeaders());
            AddBookmarkCommand addBookmarkCommand = InputOutputMapper.buildAddBookmarkCommandFromAddBookmarkInput.apply(addBookmarkInput, origin);
            capsuleService.addBookMark(addBookmarkCommand);
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