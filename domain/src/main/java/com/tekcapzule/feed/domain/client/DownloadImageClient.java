package com.tekcapzule.feed.domain.client;

public interface DownloadImageClient {

    byte[] downloadImage(String url, String imageName);

}
