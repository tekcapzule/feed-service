package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.function.input.GetMyFeedInput;
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

import static com.tekcapsule.capsule.application.config.AppConstants.HTTP_STATUS_CODE_HEADER;

@Component
@Slf4j
public class GetMyFeedFunction implements Function<Message<GetMyFeedInput>, Message<List<Capsule>>> {

    private final CapsuleService capsuleService;

    public GetMyFeedFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @Override
    public Message<List<Capsule>> apply(Message<GetMyFeedInput> getMyFeedInputMessage) {

        log.info("Entering get myFeed Function");

        List<Capsule> capsules = capsuleService.getMyFeed(getMyFeedInputMessage.getPayload().getSubscribedTopics());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(capsules, responseHeader);

    }
}