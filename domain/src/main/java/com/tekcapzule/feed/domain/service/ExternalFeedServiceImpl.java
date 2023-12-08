package com.tekcapzule.feed.domain.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tekcapzule.feed.domain.command.PostCommand;
import com.tekcapzule.feed.domain.exception.FeedCreationException;
import com.tekcapzule.feed.domain.exception.S3ClientException;
import com.tekcapzule.feed.domain.model.Feed;
import com.tekcapzule.feed.domain.model.Status;
import com.tekcapzule.feed.domain.repository.FeedDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class ExternalFeedServiceImpl implements ExternalFeedService {

    @Value("#{environment.CLOUD_REGION}")
    private String region;
    @Value("#{environment.EXT_IMG_BUCKET}")
    private String extImageS3Bucket;
    private static final String PROPERTY = "property";
    private static final String CONTENT = "content";
    private FeedDynamoRepository feedDynamoRepository;

    public ExternalFeedServiceImpl(FeedDynamoRepository feedDynamoRepository){
        this.feedDynamoRepository = feedDynamoRepository;
    }

    @Override
    public void post(PostCommand postCommand) {
        log.info("Entering post method - Getting Feed from %s", postCommand.getFeedSourceUrl());
        feedDynamoRepository.save(prepareFeed(postCommand));
    }

    private Feed prepareFeed(PostCommand postCommand) {
        log.info(String.format("Entering prepareFeed method %s :: ", postCommand.getFeedSourceUrl()));
        UrlMetaTag urlMetaTag = extractDetails(postCommand.getFeedSourceUrl());
        urlMetaTag = putS3InputStream(urlMetaTag);
        return mapFeed(postCommand, urlMetaTag);
    }

    private Feed mapFeed(PostCommand postCommand, UrlMetaTag urlMetaTag) {
        return Feed.builder().title(urlMetaTag.getTitle()).description(urlMetaTag.getDescription())
                .imageUrl(urlMetaTag.getImageUrl()).publishedDate(postCommand.getExecOn()).status(Status.SUBMITTED).build();
    }

    private UrlMetaTag putS3InputStream(UrlMetaTag urlMetaTag) throws FeedCreationException {
        log.info(String.format("Entering puts3InputStream - Uploading object to bucket %s", extImageS3Bucket));
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .build();
        InputStream in = new ByteArrayInputStream(urlMetaTag.getImageData());
        String imageName = urlMetaTag.getImageName();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(urlMetaTag.getImageData().length);
            PutObjectRequest putOb = new PutObjectRequest(extImageS3Bucket, imageName, in, objectMetadata);
            amazonS3.putObject(putOb);
            log.info(String.format("Successfully placed %s into bucket %s", imageName, extImageS3Bucket));
            AmazonS3Client amazonS3Client = (AmazonS3Client) amazonS3;
            urlMetaTag.setImageUrl(amazonS3Client.getResourceUrl(extImageS3Bucket, imageName));
        } catch (AmazonS3Exception e) {
            log.error("Error uploading image, error connecting S3");
            throw new S3ClientException(e.getMessage(), e);
        }
        return urlMetaTag;
    }
    private UrlMetaTag extractDetails(String url) {
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

    private byte[] downloadImage(String url,String imageName) throws FeedCreationException{
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
        String imageUrl = null;
        String imageName = null;
        for (Element element : links) {
            attributes = element.attributes();
            if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:image")) {
                imageUrl = attributes.get(CONTENT);
                imageName = getImageName(attributes.get(CONTENT));
                urlMetaTagBuilder.imageUrl(imageUrl);
                urlMetaTagBuilder.imageName(imageName);
            } else if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:title")) {
                attributes = element.attributes();
                urlMetaTagBuilder.title(attributes.get(CONTENT));
            } else if (attributes.get(PROPERTY) != null && attributes.get(PROPERTY).equals("og:description")) {
                attributes = element.attributes();
                urlMetaTagBuilder.description(attributes.get(CONTENT));
            }
        }
        urlMetaTagBuilder.imageData(downloadImage(imageUrl, imageName));
        return urlMetaTagBuilder.build();
    }

    private String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/')+1);
    }
}
