package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConfig;
import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.capsule.application.function.input.CreateInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
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
public class CreateFunction implements Function<Message<CreateInput>, Message<Void>> {

    private final CapsuleService capsuleService;

    private final AppConfig appConfig;

    public CreateFunction(final CapsuleService capsuleService, final AppConfig appConfig) {
        this.capsuleService = capsuleService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateInput> createInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            CreateInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create capsule Function -  Capsule Name:%s", createInput.getTitle()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
            capsuleService.create(createCommand);
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