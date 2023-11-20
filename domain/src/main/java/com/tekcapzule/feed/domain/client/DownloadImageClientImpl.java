package com.tekcapzule.feed.domain.client;

import com.tekcapzule.feed.domain.exception.FeedCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
@Slf4j
@Component
public class DownloadImageClientImpl implements DownloadImageClient {
    @Override
    public byte[] downloadImage(String url,String imageName) throws FeedCreationException{
        log.info(String.format("Entering downloadImage url :: %s, imageName :: %s", url, imageName));
        BufferedImage bufferedImage = null;
        try {
            URL urlA =new URL(url);
            bufferedImage = ImageIO.read(urlA);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imageName.substring(imageName.indexOf(".")+1), baos);
            return baos.toByteArray();
        } catch (MalformedURLException e) {
            log.error("Error downloading image, malformed url");
            throw new FeedCreationException(e.getMessage(), e);
        } catch (IOException e) {
            log.error("Error downloading image, connection failed");
            throw new FeedCreationException(e.getMessage(), e);
        }
    }
}
