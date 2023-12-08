package com.tekcapzule.feed.domain.exception;

public class FeedCreationException extends RuntimeException{

    public FeedCreationException(String message, Exception e){
        super(message, e);
    }
}
