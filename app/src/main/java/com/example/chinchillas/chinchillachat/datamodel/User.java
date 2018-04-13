package com.example.chinchillas.chinchillachat.datamodel;

/**
 * Created by ivyvecna15 on 4/13/2018.
 */

public class User {
    private String username;
    private String email;
    private String pseudoUsername;

    public User(String username, String email, String pseudoUsername) {
        this.username = username;
        this.email = email;
        this.pseudoUsername = pseudoUsername;
    }
}
