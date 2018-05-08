package com.example.chinchillas.chinchillachat.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Timer;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class Message implements Comparable<Message> {
    public String message;
    public String senderID; // actually sender username
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

    public void setTime(long time) {
        this.time = time;
    }
}
