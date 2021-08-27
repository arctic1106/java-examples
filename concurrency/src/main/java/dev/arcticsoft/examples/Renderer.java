package dev.arcticsoft.examples;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Renderer
 *
 * @list 6.15
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * Example of rendering HTML elements by using CompletionService to render page
 * elements as they become available.
 */
public abstract class Renderer {

    private final ExecutorService executor;

    public Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    public void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (final ImageInfo imageInfo : info) {
	        // Create a separate task for downloading each image and execute them in a thread pool, turning the sequential download into a parallel one
            completionService.submit(imageInfo::downloadImage);
        }

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();                // Fetch results from the CompletionService and rendering each image as soon as it is available
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

        }
    }

    interface ImageData {
    }

    interface ImageInfo {

        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);

}
