package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConstants;
import com.tekcapsule.capsule.application.function.input.SearchByTopicInput;
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
public class SearchByTopicFunction implements Function<Message<SearchByTopicInput>, Message<List<Capsule>>> {

    private final CapsuleService capsuleService;

    public SearchByTopicFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @Override
    public Message<List<Capsule>> apply(Message<SearchByTopicInput> findByTopicInputMessage) {
        SearchByTopicInput searchByTopicInput = findByTopicInputMessage.getPayload();

        log.info(String.format("Entering search by topic Function", searchByTopicInput.getSubscribedTopic()));

        List<Capsule> capsules = capsuleService.findByTopic(searchByTopicInput.getSubscribedTopic());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        return new GenericMessage(capsules, responseHeader);
    }
}