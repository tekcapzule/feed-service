package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.query.SearchItem;
import com.tekcapsule.capsule.domain.query.SearchQuery;

import java.util.List;

public interface CapsuleService {

    Capsule create(CreateCommand createCommand);

    Capsule update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Capsule get(String tenantId, String userId);
}
