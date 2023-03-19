package com.tekcapsule.capsule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExpiryInterval {
    ONEWEEK("OneWeek"),
    ONEMONTH("OneMonth"),
    SIXMONTHS("SixMonths"),
    NOEXPIRY("NoExpiry");

    @Getter
    private String value;
}