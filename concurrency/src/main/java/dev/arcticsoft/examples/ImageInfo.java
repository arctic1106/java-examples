package dev.arcticsoft.examples;

import java.util.Random;

public class ImageInfo {

    final String imageLink;

    public ImageInfo(String imageLink) {
        this.imageLink = imageLink;
    }

    public ImageData downloadImage() {
        int delay = new Random().nextInt(100);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
        System.out.println("Downloaded image " + delay + " Bytes by " + Thread.currentThread());
        return new ImageData("Image downloaded from: " + imageLink);
    }
}
