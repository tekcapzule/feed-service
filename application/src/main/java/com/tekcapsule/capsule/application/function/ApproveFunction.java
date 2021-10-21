package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.function.input.ApproveCapsuleInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
import com.tekcapsule.capsule.domain.command.ApproveCommand;
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
public class ApproveFunction implements Function<Message<ApproveCapsuleInput>, Message<Void>> {

    private final CapsuleService capsuleService;

    public ApproveFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }


    @Override
    public Message<Void> apply(Message<ApproveCapsuleInput> approveCapsuleInputMessage) {
        ApproveCapsuleInput approveCapsuleInput = approveCapsuleInputMessage.getPayload();

        log.info(String.format("Entering approve capsule Function -  Capsule Id:{0}",  approveCapsuleInput.getCapsuleId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(approveCapsuleInputMessage.getHeaders());

        ApproveCommand approveCommand =InputOutputMapper.buildApproveCapsuleCommandFromApproveCapsuleInput.apply(approveCapsuleInput, origin);
        capsuleService.approve(approveCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(responseHeader);

    }
}