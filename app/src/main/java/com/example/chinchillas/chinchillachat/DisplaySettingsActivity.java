package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This activity allows the user to select new themes
 * for their app display.
 */

public class DisplaySettingsActivity extends ChinchillaChatActivity {

    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);

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