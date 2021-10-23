package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConstants;
import com.tekcapsule.capsule.application.function.input.GetInput;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public GetFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @Override
    public Message<Capsule> apply(Message<GetInput> getInputMessage) {
        GetInput getInput = getInputMessage.getPayload();

        log.info(String.format("Entering get capsule Function - Capsule Id:%s", getInput.getCapsuleId()));

        Capsule capsule = capsuleService.findBy(getInput.getCapsuleId());
        Map<String, Object> responseHeader = new HashMap<>();
        if (capsule == null) {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.NOT_FOUND.value());
            capsule = Capsule.builder().build();
        } else {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        }
        return new GenericMessage<>(capsule, responseHeader);
    }
}