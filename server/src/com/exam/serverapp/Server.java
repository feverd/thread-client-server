package com.exam.serverapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

//import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private Connection connection;
    private ArrayBlockingQueue<Message> messagesQueue;
    private CopyOnWriteArrayList<Connection> connections;

    public Server(int port) {
        this.port = port;
       messagesQueue = new ArrayBlockingQueue<>(10);
       connections = new CopyOnWriteArrayList<>();
    }

    public void start() {
       ServerWriter writer = new ServerWriter(messagesQueue, port, connections);
        Thread writerThread = new Thread(writer);
        writerThread.start();

        ServerReader reader = new ServerReader(messagesQueue, port, connection, connections);
        Thread readerThread = new Thread(reader);
        readerThread.start();

        /*try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");

            while (true) {
                //Thread readThread = new Thread(new ServerReader());

                Socket newClient = serverSocket.accept(); // момент установки соединения с клиентом
                connection = new Connection(newClient); //принимаеи и отправляем сообщение
                connections.add(connection);

                Message message = (Message) connection.readMessage();
                //clients.add(message.getSender());
                System.out.println(message);

                connection.sendMessage(Message.getMessage("Server", "jhgk"));


            }
        } catch (IOException e) {
            System.out.println("Server error");
        } catch (ClassNotFoundException e) {
            System.out.println("Reading message error"); // надо выбрасывать рантайм исключение,
            // тк если объекта нет он не появится
        }*/

    }
    // if переделать на отдельные методы


    /*private String getLoadResult(SimpleMessage message) {
        String result;
        ImgMessage imgMessage = (ImgMessage) message;
        ImgHandler handler = new ImgHandler(new File((UUID.randomUUID()) + "." + imgMessage.getExtension()));
        try {
            handler.writeToFile(imgMessage.getBImage());
            result = "File saved";
        } catch (IOException e) {
            result = "Save error";
        }
        return result;
    }*/
}
