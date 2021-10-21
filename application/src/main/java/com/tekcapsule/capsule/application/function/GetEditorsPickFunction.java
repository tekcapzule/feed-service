package com.tekcapsule.capsule.application.function;

import com.tekcapsule.capsule.application.function.input.UpdateInput;
import com.tekcapsule.capsule.application.mapper.InputOutputMapper;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.service.CapsuleService;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
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
public class GetEditorsPickFunction implements Function<Message<Void>, Message<List<Capsule>>> {

    private final CapsuleService capsuleService;

    public GetEditorsPickFunction(final CapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }


    @Override
    public Message<List<Capsule>> apply(Message<Void> getEditorsPickInputMessage) {

        log.info(String.format("Entering get editors pick capsule Function"));

        List<Capsule> capsules = capsuleService.getEditorsPick();
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(capsules, responseHeader);

    }
}