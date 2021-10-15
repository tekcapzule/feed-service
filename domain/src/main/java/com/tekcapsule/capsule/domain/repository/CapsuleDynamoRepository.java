package com.tekcapsule.capsule.domain.repository;

import com.tekcapsule.capsule.domain.model.Capsule;
import com.tekcapsule.core.domain.CrudRepository;


public interface CapsuleDynamoRepository extends CrudRepository<Capsule, String> {

    void disable(String id);
}
