package com.example.chinchillas.chinchillachat.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class MessageThread {
    private List<Message> messages;

    public MessageThread() {
        messages = new ArrayList<Message>();
    }

    /**
     * Add a message to a thread.
     *
     * This method could be void, but I feel like issues will come up, and it'll be
     * useful to be able to check if it worked.
     *
     * @param message
     * @return true if the message was successfully added, false otherwise
     */
    public boolean addMessage(Message message) {
        try{
            messages.add(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Message> getMessages() {
        return messages;
    }
}
