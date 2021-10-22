package com.tekcapsule.capsule.domain.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriteria {
    private String indexName;
    private String hashKeyName;
    private String rangeKeyName;
    private String hashKeyValue;
    private String rangeKeyValue;
}