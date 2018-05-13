package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Main menu.
 */

public class MainMenuActivity extends ChinchillaChatActivity {

    private Button findAFriendBtn;
    private Button chatNowBtn;
    private Button myChatsBtn;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_mainmenu);

        findAFriendBtn = findViewById(R.id.findAFriendButton);
        findAFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainMenuActivity.this, PreferencesActivity.class));
                displayComingSoonMessage();
            }
        });

        myChatsBtn = findViewById(R.id.myChatsButton);
        myChatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatListActivity.class));
            }
        });

        chatNowBtn = findViewById(R.id.chatNowButton);
        chatNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                Collection<String> userSet = (Collection<String>) userPrefs.getAll().values();
                String[] users = new String[userSet.size()];
                userSet.toArray(users);
                String chatFriend = null;
                while (chatFriend == null || chatFriend.equalsIgnoreCase(myUsername)){
                    chatFriend = users[rand.nextInt(users.length)];
                    Log.d("chatFriend", chatFriend);
                }
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                ArrayList<String> friendUsernames = new ArrayList<String>();
                friendUsernames.add(chatFriend);
                intent.putExtra("friendUsernames", friendUsernames);
                Toast.makeText(MainMenuActivity.this, "Now chatting with " + chatFriend + ". Say hello!", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
}