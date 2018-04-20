package com.example.chinchillas.chinchillachat.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class Pseudouser {
    private String pseudonym;
    private Map<String, MessageThread> chats;
    private List<User> friends;
    private List<User> blockList;

    public Pseudouser(String pseudonym) {
        this.pseudonym = pseudonym;
        chats = new HashMap<>();
        chats.put(" ",new MessageThread());
        friends = new ArrayList<>();
        blockList = new ArrayList<>();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pseudonym", pseudonym);
        map.put("chats", chats);
        map.put("friends", friends);
        map.put("blockList", blockList);
        return map;
    }
}
