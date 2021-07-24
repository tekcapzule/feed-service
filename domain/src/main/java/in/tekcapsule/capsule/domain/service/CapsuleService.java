package in.tekcapsule.capsule.domain.service;

import in.tekcapsule.capsule.domain.command.CreateCommand;
import in.tekcapsule.capsule.domain.command.DisableCommand;
import in.tekcapsule.capsule.domain.command.UpdateCommand;
import in.tekcapsule.capsule.domain.model.Mentor;
import in.tekcapsule.capsule.domain.query.SearchItem;
import in.tekcapsule.capsule.domain.query.SearchQuery;

import java.util.List;

public interface CapsuleService {

    Mentor create(CreateCommand createCommand);

    Mentor update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Mentor get(String tenantId, String userId);
}
