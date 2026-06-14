package dev.arcticsoft.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureRendererImpl extends AbstractRenderer {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void render(final CharSequence source) {
        Future<List<ImageData>> downloadImagesFuture;
        downloadImagesFuture = executor.submit(() -> {
            List<ImageData> images = new ArrayList<>();
            for (ImageInfo info : scanForImageInfo(source)) {
                images.add(info.downloadImage());
            }
            return images;
        });

        renderText(source);
        List<ImageData> images;
        try {
            images = downloadImagesFuture.get();
            for (ImageData image : images) {
                renderImage(image);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            downloadImagesFuture.cancel(true);
        } catch (ExecutionException e) {
        }
    }

    @Override
    public void stop() {
        executor.shutdown();
    }
}
