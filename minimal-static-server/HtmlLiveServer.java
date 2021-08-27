package dev.arcticsoft;

import com.sun.net.httpserver.SimpleFileServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public class HtmlLiveServer {
    private final System.Logger LOG = System.getLogger(HtmlLiveServer.class.getName());

    public void serve() {
        var port = 4000;
        var loopback = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        var path = Path.of("./src/main/resources").toAbsolutePath();
        var webServer = SimpleFileServer.createFileServer(loopback, path, SimpleFileServer.OutputLevel.VERBOSE);
        webServer.start();

        LOG.log(System.Logger.Level.INFO,
                "http://%s:%d".formatted(webServer.getAddress().getHostString(), webServer.getAddress().getPort()));
    }
}
