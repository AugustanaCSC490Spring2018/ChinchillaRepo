package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This activity allows the user to change their username
 * or password and requests confirmation.
 */

public class AccountSettingsActivity extends ChinchillaChatActivity {

    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        confirmBtn = findViewById(R.id.confirmSettingsBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement
                displayComingSoonMessage();
            }
        });
    }
}