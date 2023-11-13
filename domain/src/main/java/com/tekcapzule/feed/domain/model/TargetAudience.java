package com.tekcapzule.feed.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TargetAudience {
    DEVELOPER("Developer"),
    ENGINEERING_MANAGER("Engineering Manager"),
    EXECUTIVE("Executive"),
    ARCHITECT("Architect"),
    ALL("All");

    @Getter
    private String value;
}