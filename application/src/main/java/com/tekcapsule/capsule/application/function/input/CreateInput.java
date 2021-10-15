package com.tekcapsule.capsule.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.capsule.domain.model.CapsuleType;
import com.tekcapsule.capsule.domain.model.TargetAudience;
import com.tekcapsule.capsule.domain.model.TopicLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String topicName;
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
    private boolean editorsPick;
}
