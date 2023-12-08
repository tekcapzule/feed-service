package com.tekcapzule.feed.domain.service;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
@Data
@Builder
public class UrlMetaTag {

    private String title;
    private String description;
    private String imageName;
    private String imageUrl;
    private byte[] imageData;

    @Override
    public String toString() {
        return "UrlMetaTag{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
