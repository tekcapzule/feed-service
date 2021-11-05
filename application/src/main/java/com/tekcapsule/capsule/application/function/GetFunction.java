package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConfig;
import com.tekcapsule.capsule.application.function.input.GetInput;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
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
public class GetFunction implements Function<Message<GetInput>, Message<Capsule>> {

    private final CapsuleService capsuleService;


    private final AppConfig appConfig;

    public GetFunction(final CapsuleService capsuleService, final AppConfig appConfig) {
        this.capsuleService = capsuleService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Capsule> apply(Message<GetInput> getInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Capsule capsule = new Capsule();
        String stage = appConfig.getStage().toUpperCase();
        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get capsule Function - Capsule Id:%s", getInput.getCapsuleId()));
            capsule = capsuleService.findBy(getInput.getCapsuleId());
            if (capsule == null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
                capsule = Capsule.builder().build();
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(capsule, responseHeaders);
    }
}