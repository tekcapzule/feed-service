package in.tekcapsule.capsule.application.function;

import in.tekcapsule.capsule.application.function.input.GetInput;
import in.devstream.mentor.domain.model.Mentor;
import in.devstream.mentor.domain.service.MentorService;
import in.tekcapsule.capsule.application.config.AppConstants;
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
public class GetFunction implements Function<Message<GetInput>, Message<Mentor>> {

    private final MentorService mentorService;

    public GetFunction(final MentorService mentorService) {
        this.mentorService = mentorService;
    }


    @Override
    public Message<Mentor> apply(Message<GetInput> getInputMessage) {
        GetInput getInput = getInputMessage.getPayload();

        log.info(String.format("Entering get mentor Function - Tenant Id:{0}, User Id:{1}", getInput.getTenantId(), getInput.getUserId()));

        Mentor mentor = mentorService.get(getInput.getTenantId(), getInput.getUserId());
        Map<String, Object> responseHeader = new HashMap();
        if (mentor == null) {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.NOT_FOUND.value());
            mentor = Mentor.builder().build();
        } else {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        }
        return new GenericMessage(mentor, responseHeader);
    }
}