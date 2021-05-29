package com.exam.clientapp;

import com.exam.lib.Connection;
import com.exam.lib.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriter implements Runnable {
    private String ip;
    private int port;
    private Scanner scanner;

    public ClientWriter(Scanner scanner, String ip, int port) {
        this.scanner = scanner;
        this.ip = ip;
        this.port = port;

    }

    private void sendMessage(Message message){
        try (Connection connection = new Connection(new Socket(ip, port))){
            connection.sendMessage(message);
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
        while (true){
            System.out.println("Enter message");
            text = scanner.nextLine();
            if ("exit".equals(text)) break;
            sendMessage(Message.getMessage(userName, text));
             /*else if(text.startsWith(CommandsNames.IMG)) {
                ImgHandler handler = new ImgHandler(new File(text.split(" ")[1]));
                ImgMessage imgMessage = null;
                try {
                    imgMessage = new ImgMessage(userName, text.split(" ")[0],
                            handler.readFromFile());
                    imgMessage.setExtension(handler.getExtension());
                    sendAndPrintMessage(imgMessage);
                } catch (IOException e) {
                    System.out.println("Не удалось отправить файл");
                }
                imgMessage.setExtension(handler.getExtension());
                sendAndPrintMessage(imgMessage);
            } */
        }
    }
}
