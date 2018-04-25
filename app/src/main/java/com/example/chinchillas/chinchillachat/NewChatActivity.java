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

public class NewChatActivity extends ChinchillaChatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    EditText editText;
    RadioButton radioUsername;
    RadioButton radioPseudonym;
    Button startChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        setContentView(R.layout.activity_newchat);

        radioUsername = findViewById(R.id.usernameRadioButton);
        radioPseudonym = findViewById(R.id.pseudonymRadioButton);

        editText = findViewById(R.id.usernameToStartChat);

        startChatBtn = findViewById(R.id.start_chat_btn);
        startChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ChatActivityTest.class);
              //  Intent intent = new Intent(getApplicationContext(), MessageThreadActivity.class);
                if(radioUsername.isChecked()) {
                    // TODO: check if username is a valid username
                    intent.putExtra("username", username);
                } else if(radioPseudonym.isChecked()) {
                    // TODO: check if pseudonym is a valid pseudonym
                    intent.putExtra("pseudonym", username);
                } else {
                    Toast.makeText(NewChatActivity.this, "Please select username or pseudonym.", Toast.LENGTH_LONG).show();
                    return; // do not start new activity
                }
                // TODO: prevent starting new activity without a valid username/pseudonym
                // start activity TODO: pass it username/pseudonym and marker indicating which
                startActivity(intent);
            }
        });

    }
}
