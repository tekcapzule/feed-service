package com.tekcapsule.capsule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CapsuleType {
    ARTICLE("Article"),
    VIDEO("Video"),
    EVENT("Event"),
    BOOK("Book"),
    COURSE("Course"),
    NEWS("News")

    @Getter
    private String value;
}
