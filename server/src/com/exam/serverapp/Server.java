package com.exam.serverapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

//import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.UUID;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private Connection connectionClient;
    private ArrayBlockingQueue<Message> messagesQueue;
    private CopyOnWriteArrayList<Connection> connections;

    public Server(int port) {
        this.port = port;
        messagesQueue = new ArrayBlockingQueue<>(10);
        connections = new CopyOnWriteArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");
            Writer writer = new Writer();
            new Thread(writer).start();

            while (true) {
                Socket newClient = serverSocket.accept(); // момент установки соединения с клиентом
                connectionClient = new Connection(newClient); //принимаеи и отправляем сообщение
                connections.add(connectionClient);

                new Thread(new Reader(connectionClient)).start();

            }
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }

    private class Reader implements Runnable {
        private Connection connection;

        public Reader(Connection connection) {
            Objects.requireNonNull(connection);
            this.connection = connection;
        }

        /*private Message readMessage() {
            Message message = null;
            try {
                message = (Message) connection.readMessage();
            } catch (IOException e) {
                System.out.println("Get message error");
            } catch (ClassNotFoundException e) {
                System.out.println("Reading message error");
            }
            return message;
        }*/

        @Override
        public void run() {

            try {
                while (true) {
                    Message message = (Message) connection.readMessage();
                    connection.setSender(message.getSender());
                    messagesQueue.put(message);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Reading message error");
            } catch (InterruptedException e) {
                System.out.println("Thread error");
            } catch (IOException e) {
                System.out.println("Connection error");
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Connection error"); // какая причина
                }
            }
        }
    }

    private class Writer implements Runnable {

        private void sendMessage() {
            while (true) {
                try {

                    Message message = messagesQueue.take();
                    for (Connection connection : connections) {
                        if (message.getSender().equals(connection.getSender())) continue;
                        else connection.sendMessage(message);
                    }
                } catch (IOException e) {
                    System.out.println("Send message error");
                } catch (InterruptedException e) {
                    System.out.println("Thread interruption error");
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                if (connections.size() == 0) continue;
                else sendMessage();
            }
        }
    }
}

