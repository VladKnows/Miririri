package Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageVector {
    BufferedImage []images;
    int numberOfImages;
    int []durationOfImages;

    int onImage;
    int onFrame;
    int totalDuration = 0;

    public ImageVector(String path, String name, int numberOfImages, int[] durationOfImages) throws IOException {
        this.numberOfImages = numberOfImages;
        this.durationOfImages = durationOfImages;
        onFrame = 0;
        onImage = 0;

        images = new BufferedImage[numberOfImages];
        String place = path + "/" + name + "_";
        for(int i = 0; i < numberOfImages; i++) {
            images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(place + "" + i + ".png")));
        }

        for(int i = 0; i < numberOfImages; i++) {
            totalDuration += durationOfImages[i];
        }
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void ResetToFirst() {
        onFrame = 0;
        onImage = 0;
    }

    public BufferedImage GetImage() {
        onFrame++;
        if(onFrame >= durationOfImages[onImage]) {
            onImage++;
            if(onImage >= numberOfImages) {
                onImage = 0;
            }
            onFrame = 0;
        }
        return images[onImage];
    }

    public BufferedImage GetImage(double proportion) {
        int duration = (int) ((double) durationOfImages[onImage] / proportion);
        onFrame++;
        if(onFrame >= duration) {
            onImage++;
            if(onImage >= numberOfImages) {
                onImage = 0;
            }
            onFrame = 0;
        }
        return images[onImage];
    }
}
