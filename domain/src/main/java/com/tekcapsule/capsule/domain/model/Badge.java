package com.tekcapsule.capsule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Badge {
    NONE("None"),
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold");

    @Getter
    private String value;
}