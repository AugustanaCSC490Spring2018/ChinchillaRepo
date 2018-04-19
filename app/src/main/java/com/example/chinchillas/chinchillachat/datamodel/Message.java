package com.example.chinchillas.chinchillachat.datamodel;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class Message {
    public String message;
    public String senderID;
    public long time;

    /**
     * @param message the message
     * @param senderID
     */
    public Message(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
        time = GregorianCalendar.getInstance().getTimeInMillis();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("senderID", senderID);
        map.put("time", time);
        return map;
    }
}
