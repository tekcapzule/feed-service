package in.tekcapsule.capsule.application.function;

import in.tekcapsule.capsule.application.function.input.SearchInput;
import in.devstream.mentor.domain.query.SearchItem;
import in.devstream.mentor.domain.query.SearchQuery;
import in.devstream.mentor.domain.service.MentorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static in.tekcapsule.capsule.application.config.AppConstants.HTTP_STATUS_CODE_HEADER;


@Component
@Slf4j
public class SearchFunction implements Function<Message<SearchInput>, Message<List<SearchItem>>> {

    private final MentorService mentorService;

    public SearchFunction(final MentorService mentorService) {
        this.mentorService = mentorService;
    }


    @Override
    public Message<List<SearchItem>> apply(Message<SearchInput> searchInputMessage) {
        SearchInput searchInput = searchInputMessage.getPayload();

        log.info(String.format("Entering search mentor Function - Tenant Id:{0}", searchInput.getTenantId()));

        List<SearchItem> searchItems = mentorService.search(SearchQuery.builder().tenantId(searchInput.getTenantId()).build());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(searchItems, responseHeader);
    }
}