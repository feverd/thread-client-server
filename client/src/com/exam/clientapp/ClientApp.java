package com.exam.clientapp;

public class ClientApp {
    public static void main(String[] args) {
        new Client("127.0.0.1", 8999).start();
    }
}
