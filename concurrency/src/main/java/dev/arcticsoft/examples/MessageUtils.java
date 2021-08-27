package dev.arcticsoft.examples;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageUtils {

    public static void sendMessage(final Socket socket, final String message) throws IOException {
        var socketOutputStream = socket.getOutputStream();
        var ois = new ObjectOutputStream(socketOutputStream);
        ois.writeUTF(message);
        ois.flush();
    }

    public static String getMessage(final Socket socket) throws IOException {
        var socketInputStream = socket.getInputStream();
        var ois = new ObjectInputStream(socketInputStream);
        return ois.readUTF();
    }
}
