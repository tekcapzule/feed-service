package com.tekcapsule.capsule.domain.service;

import com.tekcapsule.capsule.domain.command.CreateCommand;
import com.tekcapsule.capsule.domain.command.DisableCommand;
import com.tekcapsule.capsule.domain.command.UpdateCommand;
import com.tekcapsule.capsule.domain.model.Capsule;


public interface CapsuleService {

    Capsule create(CreateCommand createCommand);

    Capsule update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    Capsule get( String userId);
}
