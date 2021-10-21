package com.tekcapsule.capsule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    EXPIRED("Expired"),
    SUBMITTED("Submitted");

    @Getter
    private String value;
}