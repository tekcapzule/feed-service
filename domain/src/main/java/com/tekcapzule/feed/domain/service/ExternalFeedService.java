package com.tekcapzule.feed.domain.service;

import com.tekcapzule.feed.domain.command.PostCommand;

public interface ExternalFeedService {
    void post(PostCommand postCommand);
}
