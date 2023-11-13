package com.tekcapzule.feed.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.feed.domain.model.FeedType;
import com.tekcapzule.feed.domain.model.TargetAudience;
import com.tekcapzule.feed.domain.model.TopicLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UpdateInput {
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
