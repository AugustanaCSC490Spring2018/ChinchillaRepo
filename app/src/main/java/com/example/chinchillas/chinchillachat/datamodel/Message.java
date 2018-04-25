package com.example.chinchillas.chinchillachat.datamodel;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class Message implements Comparable<Message> {
    public String message;
    public String senderID;  //TODO: consider only storing the PSEUDONYM in each message, for security.
    public long time;

    public Message() {
        message = "";
        senderID = "";
        time = 0;
    }

    /**
     * @param message the message
     * @param senderID
     */
    public Message(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
        time = GregorianCalendar.getInstance().getTimeInMillis();
    }

    public int compareTo(Message other){
        if(time < other.time) {
            return -1;
        } else { // assume one comes before the other in all instances
            return 1;
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("senderID", senderID);
        map.put("time", time);
        return map;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderID() {
        return senderID;
    }

    public long getTime() {
        return time;
    }
}
