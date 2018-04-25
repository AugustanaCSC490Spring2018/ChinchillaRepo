package com.example.chinchillas.chinchillachat.datamodel;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    @Exclude
    public Set<String> getParticipants() {
        Set<String> users = new TreeSet<>();
        for (Message message: messages){
            users.add(message.getSenderID());
        }
        return users;
    }
}
