package com.tekcapsule.capsule.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.capsule.domain.model.*;
import com.tekcapsule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
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
    private CapsuleType type;
    private TargetAudience audience;
    private TopicLevel level;
    private String expiryDate;
    private List<String> keyPoints;
    private Integer editorsPick;
    private List<Quiz> quizzes;
}
