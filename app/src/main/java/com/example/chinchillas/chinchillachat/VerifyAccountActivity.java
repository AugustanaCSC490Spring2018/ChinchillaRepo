package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ivyvecna15 on 4/15/2018.
 */

public class VerifyAccountActivity extends ChinchillaChatActivity {

    private FirebaseAuth firebaseAuth;
    private Button resendButton;
    private Button continueButton;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            password = extras.getString("password");
        }
        setContentView(R.layout.activity_verifyaccount);
        firebaseAuth.signInWithEmailAndPassword(username, password);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification();

        resendButton = findViewById(R.id.resend_verification_btn);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                user.sendEmailVerification();
            }
        });
        continueButton = findViewById(R.id.check_verification_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().reload();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user.isEmailVerified()) {
                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                } else {
                    Toast.makeText(VerifyAccountActivity.this, "Please verify your email first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
