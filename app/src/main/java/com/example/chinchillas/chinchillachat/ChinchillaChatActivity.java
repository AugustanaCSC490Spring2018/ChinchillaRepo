package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.securepreferences.SecurePreferences;

/**
 * This class extends AppCompatActivity and can be extended by any other activity.
 *
 * Basically it just exists to make implementing cross-activity features more concise by
 * having them inherit them.
 */

public abstract class ChinchillaChatActivity extends AppCompatActivity {

    // used for theme, login
    protected SecurePreferences pref;

    protected SharedPreferences.Editor editor;

//    This class uses SecurePreferences
//    https://github.com/scottyab/secure-preferences
//
//     Copyright (C) 2013, Daniel Abraham, Scott Alexander-Bown
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.


    protected boolean nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SecurePreferences(getApplicationContext());
        editor = pref.edit();
        nightMode = Boolean.parseBoolean(pref.getString("nightmode", null));
        if (nightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu;
        // this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                editor.remove("userid");
                editor.commit();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            case R.id.action_settings:
                //  startActivity(new Intent(getApplicationContext(), .class));
                return true;
            case R.id.action_blockedusers:
                //   startActivity(new Intent(getApplicationContext(), .class));
                return true;
            case R.id.action_nightmode:
                nightMode = !nightMode;
                editor.putBoolean("nightmode", nightMode);
                editor.commit();
                Toast.makeText(this, "Please restart app to " + (nightMode ? "enable" : "disable") + " night mode.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
