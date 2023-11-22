package com.tekcapzule.feed.domain.client;

import com.tekcapzule.feed.domain.exception.FeedCreationException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UrlMetaTagExtractorClientImpl implements UrlMetaTagExtractorClient {

    private static final String PROPERTY = "property";
    private static final String CONTENT = "content";
    @Override
    public UrlMetaTag extractDetails(String url) {
        log.info("Entering extractDetails");
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(String.format("Error Connecting the url :: %s", url));
            throw new FeedCreationException(e.getMessage(), e);
        }
        Elements links = doc.select("meta");
        return mapUrlMetaTag(links);
    }

    private UrlMetaTag mapUrlMetaTag(Elements links) {
        UrlMetaTag urlMetaTag = new UrlMetaTag();
        Attributes attributes = null;
        for(int i=0; i< links.size() ; i++){
            Element element = links.get(i);
            attributes = element.attributes();
            if(attributes.get(PROPERTY)!=null && attributes.get(PROPERTY).equals("og:image")){
                urlMetaTag.setImageUrl(attributes.get(CONTENT));
                urlMetaTag.setImageName(getImageName(attributes.get(CONTENT)));
            } else if(attributes.get(PROPERTY)!=null && attributes.get(PROPERTY).equals("og:title")){
                attributes = element.attributes();
                urlMetaTag.setTitle(attributes.get(CONTENT));
            } else if(attributes.get(PROPERTY)!=null && attributes.get(PROPERTY).equals("og:description")){
                attributes = element.attributes();
                urlMetaTag.setDescription(attributes.get(CONTENT));
            }
        }
        return urlMetaTag;
    }

    private String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/')+1);
    }
}
