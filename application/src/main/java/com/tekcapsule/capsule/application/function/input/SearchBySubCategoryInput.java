package com.tekcapsule.capsule.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SearchBySubCategoryInput {
    private String topicCode;
    private String category;
    private String subCategory;
}
