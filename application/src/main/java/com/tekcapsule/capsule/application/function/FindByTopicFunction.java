package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConstants;
import com.tekcapsule.capsule.application.function.input.FindByTopicInput;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class FindByTopicFunction implements Function<Message<FindByTopicInput>, Message<List<Capsule>>> {

    private final CapsuleService capsuleService;

    public FindByTopicFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @Override
    public Message<List<Capsule>> apply(Message<FindByTopicInput> findByTopicInputMessage) {
        FindByTopicInput findByTopicInput = findByTopicInputMessage.getPayload();

        log.info(String.format("Entering find by topic Function", findByTopicInput.getSubscribedTopics()));

        List<Capsule> capsules = capsuleService.findByTopic(findByTopicInput.getSubscribedTopics());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        return new GenericMessage(capsules, responseHeader);
    }
}