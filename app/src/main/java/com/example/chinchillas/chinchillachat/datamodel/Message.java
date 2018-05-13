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
 * Messages store the message data of a chat.
 * A message contains a String with the actual data,
 * a String indicating the username of the person who
 * sent the message, and a long indicating the time
 * the message was sent (as determined by GregorianCalendar).
 */

public class Message implements Comparable<Message> {
    public String message;
    public String senderID; // actually sender username
    public long time;

    /**
     * Empty constructor. Creates a message with no sender and
     * no text.
     */
    public Message() {
        message = "";
        senderID = "";
        time = 0;
    }

    /**
     * @param message the message
     * @param senderID sender username
     */
    public Message(String message, String senderID) {
        this.message = message;
        this.senderID = senderID;
        time = GregorianCalendar.getInstance().getTimeInMillis();
    }

    /**
     * One message comes before another if the message was sent at an
     * earlier time. To avoid collision, we will always assume two messages
     * were sent at different times.
     *
     * @param other
     * @return -1 if this message came earlier; 1 otherwise
     */
    public int compareTo(Message other){
        if(time < other.time) {
            return -1;
        } else { // assume one comes before the other in all instances
            return 1;
        }
    }

    /**
     * Creates a map for JSON objectification.
     *
     * @return map representation of this object
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("senderID", senderID);
        map.put("time", time);
        return map;
    }

    /**
     * @return message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return message sender username
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * @return time message was sent
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the time to parameter.
     *
     * @param time
     */
    public void setTime(long time) {
        this.time = time;
    }
}
