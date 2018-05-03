package com.example.chinchillas.chinchillachat;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;

/**
 * Created by Angelica Garcia on 4/16/2018.
 */

/**
 * This class defines the Settings activity in the application.
 * It should have options
 */

public class SettingsActivity extends ChinchillaChatActivity {

    private boolean notifPref;
    private CheckBoxPreference checkBoxPreference;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        notifPref = pref.getBoolean("checkboxPref", true);

//        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                if (newValue.toString().equals("true")) {
//                    // let app do push notifications
//                } else {
//                    // turn off push notifications
//                }
//                return true;
//            }
//        });

        confirmBtn = findViewById(R.id.confirm_settings);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement
                displayComingSoonMessage();
            }
        });
    }
}
