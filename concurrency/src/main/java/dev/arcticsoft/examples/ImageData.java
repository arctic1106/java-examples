package dev.arcticsoft.examples;

public class ImageData {

    private final String imageData;

    public ImageData(String imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "Some image data{"
                + "imageData='" + imageData + '\''
                + '}';
    }
}
