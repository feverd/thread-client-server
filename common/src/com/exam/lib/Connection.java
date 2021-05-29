package com.exam.lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class Connection implements AutoCloseable {
    private Socket socket;
    private ObjectInputStream input; //сделать из объектов байты
    private ObjectOutputStream output; // из байтов объект
    private UUID uuid;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void sendMessage(Message message) throws IOException {
        message.setDateTime();
        output.writeObject(message);
        output.flush();
    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }


    @Override
    public void close() throws Exception {
        input.close();
        output.close();
        socket.close();
    }
}
