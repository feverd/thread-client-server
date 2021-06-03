package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Scanner scanner;

    public Client(String ip, int port) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(port);
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
    }

    public void start() {
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
            Objects.requireNonNull(connection);
            this.connection = connection;
        }

        private void readPrintMessage() {
            try {
                while (!connection.isClosed()) {
                    Message fromServer = connection.readMessage();
                    System.out.println("From other clients: " + fromServer);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Read error");
            } catch (Exception e) {
                System.out.println("Connection error (reader) 1 " + Thread.currentThread().getName());
            } finally {
                if (!connection.isClosed()) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        System.out.println("Connection error (reader) " + Thread.currentThread().getName());
                    }
                }
            }
        }


        @Override
        public void run() {
            readPrintMessage();
        }
    }

    private class ClientWriter implements Runnable {
        private Connection connection;

        public ClientWriter(Connection connection) {
            Objects.requireNonNull(connection);
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
                    connection.sendMessage(Message.getMessage(userName, text));
                    if ("exit".equals(text)) break;
                }
            } catch (IOException e) {
                System.out.println("Send message error");
            } catch (Exception e) {
                System.out.println("Connection error");
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Connection error");
                }
            }
        }

        @Override
        public void run() {
            sendMessage();
        }
    }

}


