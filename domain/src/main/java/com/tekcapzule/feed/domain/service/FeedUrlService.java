package com.tekcapzule.feed.domain.service;

import com.tekcapzule.feed.domain.exception.FeedCreationException;

public interface FeedUrlService {

    UrlMetaTag extractDetails(String url);
    byte[] downloadImage(String url,String imageName) throws FeedCreationException;
}
