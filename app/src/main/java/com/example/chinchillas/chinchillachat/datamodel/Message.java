package com.example.chinchillas.chinchillachat.datamodel;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class Message {
    public String message;
    public String senderID;

    /**
     * @param message the message
     * @param senderID
     */
    public Message(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
    }
}
