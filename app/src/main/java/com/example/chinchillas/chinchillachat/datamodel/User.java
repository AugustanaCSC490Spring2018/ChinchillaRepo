package com.example.chinchillas.chinchillachat.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class User {
    private String username;
    private String email;
//    private Pseudouser pseudouser;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
//        this.pseudouser = new Pseudouser(pseudoUsername);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
//        map.put("pseudouser", pseudouser.toMap());
        return map;
    }
}
