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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.securepreferences.SecurePreferences;

import java.util.HashSet;
import java.util.Set;


/**
 * This class extends AppCompatActivity and can be extended by any other activity.
 *
 * Basically it just exists to make implementing cross-activity features more concise by
 * having them inherit them.
 */

public abstract class ChinchillaChatActivity extends AppCompatActivity {

    // general preferences
    protected SecurePreferences pref;
    protected SharedPreferences.Editor editor;

    // map of usernames in all caps to usernames as input by user
    protected SharedPreferences userPrefs;
    protected SharedPreferences.Editor userPrefsEditor;

    // Firebase database reference
    protected DatabaseReference databaseReference;

    // my username, frequently referenced
    protected String myUsername;

    protected Set<String> setOfBlockedUsers;

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

    protected int theme;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_COOL = 1;
    public static final int THEME_NIGHT = 2;

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
        userPrefs = getApplicationContext().getSharedPreferences("userPrefs", MODE_PRIVATE);
        userPrefsEditor = userPrefs.edit();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // CREATE LIST (MAP) OF ALL USERNAMES
        databaseReference.child("usernameList").addChildEventListener(new ChildEventListener() {

            // usernames should appear in usernameList in the form:
            // key: USERNAME (in all caps)
            // value: username (as spelled by user)
            // e.g. (MYUSERNAME1, MyUsername1)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userPrefsEditor.putString(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
                userPrefsEditor.commit();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                userPrefsEditor.remove(dataSnapshot.getKey());
                userPrefsEditor.commit();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // SET THEME APPROPRIATELY.
        String themeString = pref.getString("theme",null);
        if(themeString == null) {
            theme = 0;
        } else {
            theme = Integer.parseInt(themeString);
        }
        if (theme == 1) {
            setTheme(R.style.CoolTheme);
        } else if (theme == 2){
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        // IF SIGNED IN, GET USERNAME
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            myUsername = pref.getString("myUsername", null);
            if (myUsername == null) {
                // GET USER DATA
                databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myUsername = dataSnapshot.getValue(String.class);
                        Log.d("myUsername", myUsername);
                        editor.putString("myUsername", myUsername);
                        editor.commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            setOfBlockedUsers = pref.getStringSet("setOfBlockedUsers", new HashSet<String>());

            // CHECK FOR NON-NULL BECAUSE FIREBASE IS ASYNCHRONOUS
            if(myUsername != null) {
                databaseReference.child("blockedUsers").child("blocking").child(myUsername).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        setOfBlockedUsers.add(dataSnapshot.getKey());
                        editor.putStringSet("setOfBlockedUsers", setOfBlockedUsers);
                        editor.commit();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        setOfBlockedUsers.remove(dataSnapshot.getKey());
                        editor.putStringSet("setOfBlockedUsers", setOfBlockedUsers);
                        editor.commit();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    /**
     * Set up menu. Do not include hamburger menu if user is not logged in and verified.
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // DON'T INCLUDE MENU IF USER IS NOT LOGGED IN
        if(!(this instanceof LoginActivity || this instanceof CreateAccountActivity || this instanceof VerifyAccountActivity)) {
            //Inflate the menu;
            // this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
        }
        return true;
    }

    /**
     * Set up menu.
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                editor.clear();
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
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sometimes we're behind on getting things working. This method is a default for buttons so
     * they can be there and be safely pressed without breaking.
     */
    public void displayComingSoonMessage(){
        Toast.makeText(ChinchillaChatActivity.this, "Sorry, this feature is still in development!", Toast.LENGTH_SHORT).show();
    }

}
