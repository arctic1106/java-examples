package dev.arcticsoft.examples;

import java.net.Socket;

public class ClientConnection {

    private final Socket socket;

    public ClientConnection(final Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return this.socket;
    }
}
