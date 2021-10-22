package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.function.input.RecommendInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
import com.tekcapsule.capsule.domain.command.RecommendCommand;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.tekcapsule.capsule.application.config.AppConstants.HTTP_STATUS_CODE_HEADER;

@Component
@Slf4j
public class RecommendFunction implements Function<Message<RecommendInput>, Message<Void>> {

    private final CapsuleService capsuleService;

    public RecommendFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }


    @Override
    public Message<Void> apply(Message<RecommendInput> recommendInputMessage) {
        RecommendInput recommendInput = recommendInputMessage.getPayload();

        log.info(String.format("Entering recommend capsule Function -  Capsule Id:%S",  recommendInput.getCapsuleId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(recommendInputMessage.getHeaders());

        RecommendCommand recommendCommand = InputOutputMapper.buildRecommendCommandFromRecommendInput.apply(recommendInput, origin);
        capsuleService.recommend(recommendCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(responseHeader);

    }
}