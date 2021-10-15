package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.config.AppConstants;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import in.devstream.core.domain.Origin;
import in.devstream.core.utils.HeaderUtil;
import com.tekcapsule.capsule.application.function.input.DisableInput;
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
public class DisableFunction implements Function<Message<DisableInput>, Message<Void>> {

    private final CapsuleService capsuleService;

    public DisableFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    @Override
    public Message<Void> apply(Message<DisableInput> disableInputMessage) {

        DisableInput disableInput = disableInputMessage.getPayload();

        log.info(String.format("Entering disable mentor Function - Tenant Id:{0}, User Id:{1}", disableInput.getTenantId(), disableInput.getUserId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(disableInputMessage.getHeaders());

        DisableCommand disableCommand = InputOutputMapper.buildDisableCommandFromDisableInput.apply(disableInput, origin);
        mentorService.disable(disableCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage( responseHeader);
    }
}
