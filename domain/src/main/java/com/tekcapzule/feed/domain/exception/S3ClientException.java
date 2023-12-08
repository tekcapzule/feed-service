package com.tekcapzule.feed.domain.exception;

public class S3ClientException extends RuntimeException{

    public S3ClientException(String message, Exception e){
        super(message, e);
    }
}
