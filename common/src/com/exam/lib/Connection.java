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
    private String sender;
    private boolean closed;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        this.closed = false;
    }

    public void setSender(String sender) {
        if (sender == null) throw new IllegalArgumentException("length < 5 or null");
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public boolean isClosed() {
        return closed;
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
        closed = true;
        input.close();
        output.close();
        socket.close();
    }

    @Override
    public String toString() {
        return "Connection{" +
                "socket=" + socket +
                '}';
    }
}
