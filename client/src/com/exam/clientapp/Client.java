package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Scanner scanner;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
    }

    public void start() {

        /*System.out.println("Enter name");
        String userName = scanner.nextLine();
        String text;
        try (Connection connection = new Connection(new Socket(ip, port))) {
            while (true) {
                System.out.println("Enter message");
                text = scanner.nextLine();
                if ("exit".equals(text)) break;

                // вынести за цикл
                connection.sendMessage(new Message(userName, text));
                Message fromServer = connection.readMessage();
                System.out.println("from server: " + fromServer);
            }
        } catch (IOException e) {
            System.out.println("Send or Get message error");
        } catch (ClassNotFoundException e) {
            System.out.println("Read error"); // обычно если объекта нет в проге или он выглядит совсем уж не так
        } catch (Exception e) {
            System.out.println("Connection error");
        }*/





        Thread readerThread = null;
        Thread writerThread = null;
        try {
            Connection connection = new Connection(new Socket(ip, port));
            readerThread = new Thread(new ClientReader(connection));
            writerThread = new Thread(new ClientWriter(connection));
        } catch (IOException e) {
            System.out.println("Connection error");
        }


        readerThread.start();
        writerThread.start();

    }

    private class ClientReader implements Runnable {
        private Connection connection;

        public ClientReader(Connection connection) {
            this.connection = connection;
        }

        private void printMessage() {
            try { // скорее всего переделать, чтоб у одного клиента был один коннектион
                while (true) {
                    Message fromServer = connection.readMessage();
                    System.out.println("From other clients: " + fromServer);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Read error");
            } catch (Exception e) {
                System.out.println("Connection error");
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            printMessage();
        }
    }

    private class ClientWriter implements Runnable {
        private Connection connection;

        public ClientWriter(Connection connection) {
            this.connection = connection;
        }

        private void sendMessage() {

            System.out.println("Enter name");
            String userName = scanner.nextLine();
            String text;

            try  {
                while (true) {
                    System.out.println("Enter message");
                    text = scanner.nextLine();
                    if ("exit".equals(text)) break;
                    connection.sendMessage(Message.getMessage(userName, text));
                }
            } catch (IOException e) {
                System.out.println("Send message error");
            } catch (Exception e) {
                System.out.println("Connection error");
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {

            sendMessage();
        }
    }

}


