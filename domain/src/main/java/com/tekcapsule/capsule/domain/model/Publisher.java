package com.tekcapsule.capsule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Publisher {
    UDEMY("Udemy"),
    MEDIUM("Medium"),
    YOUTUBE("Youtube"),
    PLURALSIGHT("Pluralsight"),
    COURSERA("Coursera");

    @Getter
    private String value;
}