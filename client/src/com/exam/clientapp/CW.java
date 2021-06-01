/*package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CW implements Runnable {
    private String ip;
    private int port;
    private Scanner scanner;

    public CW(Scanner scanner, String ip, int port) {
        this.scanner = scanner;
        this.ip = ip;
        this.port = port;

    }

    private void sendMessage(Message message) {
        try (Connection connection = new Connection(new Socket(ip, port))) { // вынести конектион за цикл,// чтоб он не закрывался
            while (true) {
                connection.sendMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Send message error");
        } catch (Exception e) {
            System.out.println("Connection error");
        }
    }


    @Override
    public void run() {
        System.out.println("Enter name");
        String userName = scanner.nextLine();
        String text;

        try (Connection connection = new Connection(new Socket(ip, port))) {
            while (true) {
                System.out.println("Enter message");
                text = scanner.nextLine();
                if ("exit".equals(text)) break;
                connection.sendMessage(Message.getMessage(userName, text));
                //sendMessage(Message.getMessage(userName, text));
            }
        } catch (IOException e) {
            System.out.println("Send message error");
        } catch (Exception e) {
            System.out.println("Connection error");
        }

    }
}*/

