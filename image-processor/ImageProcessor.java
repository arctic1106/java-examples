import java.io.IOException;

public class ImageProcessor {

    public record PixelData(int[][] pixels, int width, int height) {
    }

    public static PixelData loadPixels(String resourceName) throws IOException {
        var url = ImageProcessor.class.getResource(resourceName);
        if (url == null)
            throw new IOException("Resource not found: " + resourceName);

        try (var stream = url.openStream()) {
            var image = javax.imageio.ImageIO.read(stream);
            if (image == null)
                throw new IOException("Unsupported image format");

            int width = image.getWidth();
            int height = image.getHeight();
            int[][] result = new int[height][width];

            int[] flatArray = image.getRGB(0, 0, width, height, null, 0, width);

            for (int y = 0; y < height; y++) {
                System.arraycopy(flatArray, y * width, result[y], 0, width);
            }

            return new PixelData(result, width, height);
        }
    }

    public static void main(String[] args) {
        try {
            var data = loadPixels("bandera.bmp");
            System.out.println("Loaded image: " + data.width() + "x" + data.height());
        } catch (IOException e) {
            System.err.println("Failed to process image: " + e.getMessage());
        }
    }
}