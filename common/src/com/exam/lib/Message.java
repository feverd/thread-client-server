package com.exam.lib;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String sender;
    private String text;
    private LocalDateTime dateTime;

    public Message(String sender, String text) {
        setSender(sender);
        setText(text);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        if (sender == null) throw new IllegalArgumentException("length < 5 or null");
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null) throw new IllegalArgumentException("length < 5 or null");
        this.text = text;
    }

    public void setDateTime(){
        dateTime = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static Message getMessage(String sender, String text){
        return new Message(sender, text);
    }
}