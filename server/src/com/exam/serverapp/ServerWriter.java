package com.exam.serverapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerWriter implements Runnable {
    private ArrayBlockingQueue<Message> messagesQueue;
    private int port;
    // private Connection connection;
    private CopyOnWriteArrayList<Connection> connections;

    public ServerWriter(ArrayBlockingQueue<Message> messagesQueue, int port /*Connection connection*/, CopyOnWriteArrayList<Connection> connections) {
        this.messagesQueue = messagesQueue;
        this.port = port;
        //this.connection = connection;
        this.connections = connections;
    }

    private void sendMessage() {
        // проверка чтоб не отправить сообщение тому кто его писал

        connections.stream().forEach(connection -> {
            try {
                connection.sendMessage(messagesQueue.take());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            if (connections.size() == 0) continue;
            else sendMessage();
        }
    }
}
