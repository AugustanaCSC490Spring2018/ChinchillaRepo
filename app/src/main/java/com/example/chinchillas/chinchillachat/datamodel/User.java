package com.example.chinchillas.chinchillachat.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * User represents a user of the app. Users have usernames and email
 * addresses that they use to sign up.
 *
 * Initially, we had planned to use Pseudousers which would obscure
 * some of the details of users. Ultimately we decided we were making
 * our data model convoluted for no real gain. The Pseudouser class
 * has been deleted.
 */

public class User {
    private String username;
    private String email;

    /**
     * Constructs a User.
     *
     * @param username
     * @param email
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * Creates a map for JSON objectification.
     *
     * @return map representation of this object
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        return map;
    }
}
