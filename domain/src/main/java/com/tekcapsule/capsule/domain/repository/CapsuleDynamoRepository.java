package com.tekcapsule.capsule.domain.repository;

import in.devstream.core.domain.CrudRepository;
import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.capsule.domain.query.SearchItem;

import java.util.List;

public interface CapsuleDynamoRepository extends CrudRepository<Capsule, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}
