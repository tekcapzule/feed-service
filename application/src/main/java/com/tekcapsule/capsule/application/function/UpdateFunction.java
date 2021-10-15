package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.capsule.application.function.input.UpdateInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
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
public class UpdateFunction implements Function<Message<UpdateInput>, Message<Capsule>> {

    private final CapsuleService capsuleService;

    public UpdateFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }


    @Override
    public Message<Capsule> apply(Message<UpdateInput> updateInputMessage) {
        UpdateInput updateInput = updateInputMessage.getPayload();

        log.info(String.format("Entering update mentor Function - Tenant Id:{0}, User Id:{1}", updateInput.getTenantId(), updateInput.getUserId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());

        UpdateCommand updateCommand = InputOutputMapper.buildUpdateCommandFromUpdateInput.apply(updateInput, origin);
        Mentor mentor = mentorService.update(updateCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(mentor, responseHeader);

    }
}