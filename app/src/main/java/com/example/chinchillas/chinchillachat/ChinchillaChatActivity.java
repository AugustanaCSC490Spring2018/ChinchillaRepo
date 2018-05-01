package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    protected DatabaseReference databaseReference;

    protected String username;

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
        // PREVENT USER FROM ACCESSING APP IF LOGGED OUT
        if(!(this instanceof LoginActivity || this instanceof CreateAccountActivity || this instanceof VerifyAccountActivity)){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if(firebaseAuth.getCurrentUser() == null){ // user logged out
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            // FORCE EMAIL VERIFICATION
            } else if(!firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(getApplicationContext(), VerifyAccountActivity.class));
                finish();
            }
        }
        super.onCreate(savedInstanceState);
        pref = new SecurePreferences(getApplicationContext());
        editor = pref.edit();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        nightMode = Boolean.parseBoolean(pref.getString("nightmode", null));
        if (nightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            username = pref.getString("username", null);
            if (username == null) {
                databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        username = dataSnapshot.getValue(String.class);
//                    Log.d("username", username);
                        editor.putString("username", username);
                        editor.commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu;
        // this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // TODO: Adapt for logout option being visible appropriately...
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
                // TODO: Add sassy message if they try to click while logged out.
                editor.remove("userid");
                editor.commit();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_blockedusers:
                startActivity(new Intent(getApplicationContext(), BlockedUsersActivity.class));
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

    public void displayComingSoonMessage(){
        Toast.makeText(ChinchillaChatActivity.this, "Coming soon to an Android device near you!", Toast.LENGTH_SHORT).show();
    }

}
