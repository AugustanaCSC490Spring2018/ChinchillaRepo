package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Allows the user to see "matches" - other users that are interested in the same things.
 * The user may wish to talk to those people.
 */

public class MatchesActivity extends ChinchillaChatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

    }

}