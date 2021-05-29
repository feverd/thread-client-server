package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

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

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void start(){
        Thread readerThread = new Thread(new ClientReader(ip, port));
        Thread writerThread = new Thread(new ClientWriter(scanner, ip, port));

        readerThread.start();
        writerThread.start();
    }

}
