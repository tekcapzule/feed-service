package in.tekcapsule.capsule.application.function;

import in.devstream.core.domain.Origin;
import in.devstream.core.utils.HeaderUtil;
import in.tekcapsule.capsule.application.function.input.UpdateInput;
import in.tekcapsule.capsule.application.mapper.InputOutputMapper;
import in.devstream.mentor.domain.command.UpdateCommand;
import in.devstream.mentor.domain.model.Mentor;
import in.devstream.mentor.domain.service.MentorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static in.tekcapsule.capsule.application.config.AppConstants.HTTP_STATUS_CODE_HEADER;

@Component
@Slf4j
public class UpdateFunction implements Function<Message<UpdateInput>, Message<Mentor>> {

    private final MentorService mentorService;

    public UpdateFunction(final MentorService mentorService) {
        this.mentorService = mentorService;
    }


    @Override
    public Message<Mentor> apply(Message<UpdateInput> updateInputMessage) {
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