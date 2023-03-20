package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConfig;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
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

    private final CapsuleService capsuleService;

    private final AppConfig appConfig;

    public GetMetadataFunction(final CapsuleService capsuleService, final AppConfig appConfig) {
        this.capsuleService = capsuleService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Map<String,List<String>>> apply(Message<EmptyFunctionInput> message) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String,List<String>> metaData = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info(String.format("Entering get Capsule Metadata Function"));
            metaData = capsuleService.getMetadata();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(metaData, responseHeaders);
    }
}