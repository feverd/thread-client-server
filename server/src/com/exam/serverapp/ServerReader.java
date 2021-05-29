package com.exam.serverapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerReader implements Runnable {
    private Message message;
    private ArrayBlockingQueue<Message> messagesQueue;
    private int port;
    private Connection connection;
    private CopyOnWriteArrayList<Connection> connections;

    public ServerReader(ArrayBlockingQueue<Message> messagesQueue, int port, Connection connection, CopyOnWriteArrayList<Connection> connections) {
        this.messagesQueue = messagesQueue;
        this.port = port;
        this.connection = connection;
        this.connections = connections;
    }


    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) { // так получаетс, чо он одно и то же сообщение будет туда кидать постоянно пока новое не прилетит

                Socket newClient = serverSocket.accept(); // момент установки соединения с клиентом
                connection = new Connection(newClient); //принимаеи и отправляем сообщение
                connections.add(connection);

                Message message = (Message) connection.readMessage();
                messagesQueue.put(message);

                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Server error");
        } catch (ClassNotFoundException e) {
            System.out.println("Reading message error"); // надо выбрасывать рантайм исключение,
            // тк если объекта нет он не появится
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

