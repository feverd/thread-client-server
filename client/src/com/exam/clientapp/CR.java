/*package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.net.Socket;

public class CR implements Runnable {
    private String ip;
    private int port;

    public CR(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    private void printMessage() {
        try (Connection connection = new Connection(new Socket(ip, port))){ // скорее всего переделать, чтоб у одного клиента был один коннектион
            Message fromServer = connection.readMessage();
            System.out.println("From other clients: " + fromServer);
        } catch (ClassNotFoundException e) {
            System.out.println("Read error");
        } catch (Exception e) {
            System.out.println("Connection error");
        }
    }


    @Override
    public void run() {
        while (true){
            printMessage();
        }
    }
}*/
