package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;


/**
 * This class defines the Settings activity in the application.
 * It should allow users to enable/disable push notifications.
 * It also has buttons for the user to reach an Account Settings
 * activity and a Display Settings activity.
 */

public class SettingsActivity extends ChinchillaChatActivity {

    //private boolean notifPref;
    //private CheckBoxPreference checkBoxPreference;
    private Button accountSettingsBtn;
    private Button displayBtn;
//    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //notifPref = pref.getBoolean("checkboxPref", true);

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

        accountSettingsBtn = findViewById(R.id.accountSettingsBtn);
        displayBtn = findViewById(R.id.displaySettingsBtn);
//        confirmBtn = findViewById(R.id.confirm_settings);

        accountSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountSettingsActivity.class));
            }
        });

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DisplaySettingsActivity.class));
            }
        });

//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO: Implement
//                displayComingSoonMessage();
//            }
//        });
    }
}
