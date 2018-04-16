package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;

public class ChatListActivity extends ChinchillaChatActivity {
    private Button newChatBtn;
    private ScrollView scrollView;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_chatlist);

        newChatBtn = findViewById(R.id.newChatBtn);
        newChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewChatActivity.class));
            }
        });


        scrollView = findViewById(R.id.scrollChats);
        // TODO: ADD CODE TO FILL SCROLLVIEW WITH CURRENT CHATS

    }

}
