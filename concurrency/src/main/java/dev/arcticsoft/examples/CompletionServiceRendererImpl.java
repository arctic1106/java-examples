package dev.arcticsoft.examples;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceRendererImpl extends AbstractRenderer {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public void render(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);

        for (final ImageInfo info : imageInfos) {
            completionService.submit(info::downloadImage);
        }

        renderText(source);

        for (int i = 0; i < imageInfos.size(); i++) {
            try {
                renderImage(completionService.take().get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
            }
        }

    }

    @Override
    public void stop() {
        executor.shutdown();
    }
}
