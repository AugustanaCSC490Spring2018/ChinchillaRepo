package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by angelicagarcia16 on 3/28/2018.
 */

public class MainMenuActivity extends ChinchillaChatActivity {

    private Button findAFriendBtn;
    private Button chatNowBtn;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
        }
        setContentView(R.layout.activity_mainmenu);

        findAFriendBtn = findViewById(R.id.findAFriendButton);
        findAFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, PreferencesActivity.class));
            }
        });
    }
}