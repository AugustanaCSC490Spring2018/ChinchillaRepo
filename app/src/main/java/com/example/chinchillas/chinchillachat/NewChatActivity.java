package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.chinchillas.chinchillachat.datamodel.MessageThread;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewChatActivity extends ChinchillaChatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    EditText editText;
    Button startChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        setContentView(R.layout.activity_newchat);

        editText = findViewById(R.id.usernameToStartChat);

        startChatBtn = findViewById(R.id.start_chat_btn);
        startChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernames = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ChatActivityTest.class);
                //  Intent intent = new Intent(getApplicationContext(), MessageThreadActivity.class);
                // TODO: check if username is a valid username
                ArrayList<String> friendUsernames = getUsernamesListFromUsernamesString(usernames);
                if(friendUsernames.size() == 0) {
                    Toast.makeText(NewChatActivity.this, "Please enter at least one valid username.", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("friendUsernames", friendUsernames);
                startActivity(intent);
                NewChatActivity.this.finish();
            }
        });

    }

    public static ArrayList<String> getUsernamesListFromUsernamesString(String usernames) {
        ArrayList<String> usernamesList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<usernames.length(); i++){
            char character = usernames.charAt(i);
            if(Character.isLetterOrDigit(character)){ // usernames should only contain alphanumeric characters
                sb.append(character);
            } else {
                if (sb.length() > 0) {
                    // TODO: Check if username is valid...
                    usernamesList.add(sb.toString());
                    sb.delete(0,sb.length());
                }
            }
        }
        if (sb.length() > 0){
            // TODO: Check if username is valid...
            usernamesList.add(sb.toString());
            sb.delete(0,sb.length());
        }
        return usernamesList;
    }
}
