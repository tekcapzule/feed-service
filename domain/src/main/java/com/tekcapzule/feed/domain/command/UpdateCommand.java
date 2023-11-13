package com.tekcapzule.feed.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.feed.domain.model.*;
import com.tekcapzule.core.domain.Command;
import com.tekcapzule.feed.domain.model.FeedType;
import com.tekcapzule.feed.domain.model.TargetAudience;
import com.tekcapzule.feed.domain.model.TopicLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UpdateCommand extends Command {
    private String feedId;
    private String topicCode;
    private String publishedDate;
    private String title;
    private String imageUrl;
    private Integer duration;
    private String author;
    private String description;
    private List<String> tags;
    private String publisher;
    private String resourceUrl;
    private FeedType type;
    private TargetAudience audience;
    private TopicLevel level;
    private String expiryDate;
    private List<String> keyPoints;
}
