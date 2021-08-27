package dev.arcticsoft.examples;

public class RenderMain {

    public static void main(String[] args) {
        PageRenderer renderer = new CompletionServiceRendererImpl();
        renderer.render("sdsadsadsa");
        renderer.stop();
    }
}
