package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Allows the user to block another user.
 */
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

    /**
     * Adds the other user to be blocked to this user's block list. We designed blocking in a
     * redundant way to make it easier to reference, so we also add this user to the list of users
     * who are blocking the other user.
     *
     * @return true if the user is blocked successfully, false otherwise
     */
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
