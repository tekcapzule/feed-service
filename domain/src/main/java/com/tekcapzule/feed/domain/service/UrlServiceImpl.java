package com.tekcapzule.feed.domain.service;

import com.tekcapzule.feed.domain.exception.FeedCreationException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Component
public class UrlServiceImpl implements UrlService {

    private static final String PROPERTY = "property";
    private static final String CONTENT = "content";
    @Override
    public UrlMetaTag extractDetails(String url) {
        log.info("Entering extractDetails");
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(String.format("Error Connecting the url :: %s", url));
            throw new FeedCreationException(e.getMessage(), e);
        }
        Elements links = doc.select("meta");
        return mapUrlMetaTag(links);
    }

    public byte[] downloadImage(String url,String imageName) throws FeedCreationException{
        log.info(String.format("Entering downloadImage url :: %s, imageName :: %s", url, imageName));
        BufferedImage bufferedImage;
        try {
            URL imageUrl =new URL(url);
            bufferedImage = ImageIO.read(imageUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imageName.substring(imageName.indexOf(".")+1), baos);
            return baos.toByteArray();
        } catch (MalformedURLException e) {
            log.error(String.format("Error downloading image, malformed url :: %s ", url));
            throw new FeedCreationException(e.getMessage(), e);
        } catch (IOException e) {
            log.error("Error downloading image, connection failed");
            throw new FeedCreationException(e.getMessage(), e);
        }
    }

    private UrlMetaTag mapUrlMetaTag(Elements links) {
        UrlMetaTag.UrlMetaTagBuilder urlMetaTagBuilder = UrlMetaTag.builder();
        Attributes attributes;
        for (Element element : links) {
            attributes = element.attributes();
            if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:image")) {
                urlMetaTagBuilder.imageUrl(attributes.get(CONTENT));
                urlMetaTagBuilder.imageName(getImageName(attributes.get(CONTENT)));
            } else if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:title")) {
                attributes = element.attributes();
                urlMetaTagBuilder.title(attributes.get(CONTENT));
            } else if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:description")) {
                attributes = element.attributes();
                urlMetaTagBuilder.description(attributes.get(CONTENT));
            }
        }
        return urlMetaTagBuilder.build();
    }

    private String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/')+1);
    }
}
