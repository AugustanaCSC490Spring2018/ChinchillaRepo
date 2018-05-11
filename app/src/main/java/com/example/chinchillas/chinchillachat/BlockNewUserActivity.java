package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BlockNewUserActivity extends ChinchillaChatActivity {

    private EditText usernameET;
    private Button blockUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_user);
        usernameET = findViewById(R.id.usernameToBlock);
        blockUserButton = findViewById(R.id.blockUserByUsernameButton);
        blockUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blockUser()) {
                    startActivity(new Intent(getApplicationContext(), BlockedUsersActivity.class));
                    finish();
                }
            }
        });
    }

    private boolean blockUser() {
        String usernameToBlock = usernameET.getText().toString().toUpperCase();
        if(userPrefs.contains(usernameToBlock)){
            databaseReference.child("blockedUsers").child("blocking").child(myUsername).child(userPrefs.getString(usernameToBlock, null)).setValue(true);
            databaseReference.child("blockedUsers").child("blockedBy").child(userPrefs.getString(usernameToBlock, null)).child(myUsername).setValue(true);
            return true;
        } else {
            usernameET.setError(getString(R.string.user_dne));
            return false;
        }
    }
}
