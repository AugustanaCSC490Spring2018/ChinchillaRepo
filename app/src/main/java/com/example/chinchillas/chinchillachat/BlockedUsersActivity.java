package com.example.chinchillas.chinchillachat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by angelicagarcia16 on 4/16/2018.
 */

/**
 * Sources: https://stackoverflow.com/questions/12470507/how-to-show-alert-dialog-when-click-on-listview
 *
 */

public class BlockedUsersActivity extends ChinchillaChatActivity {

    private Button blockUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);
        blockUserButton = findViewById(R.id.blockUserButton);
        blockUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BlockNewUserActivity.class));
                finish();
            }
        });
        createList();
    }

    public void createList() {
        final ArrayList<String> blockedUsers = new ArrayList<>();

        for (String blockedUser : setOfBlockedUsers) {
            // fill the blocked users list
            blockedUsers.add(blockedUser);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_view, blockedUsers);
        ListView blockedUsersLV = (ListView) findViewById(R.id.blockedList);
        blockedUsersLV.setAdapter(adapter);

        //ListView items open up a new activity when clicked upon
        blockedUsersLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // when the user taps a blocked user, a prompt asks if they'd like to unblock them
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        BlockedUsersActivity.this);
                adb.setTitle("Unblock This User");
                adb.setMessage("Would you like to unblock "
                        + parent.getItemAtPosition(position) + "?");

                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = blockedUsers.get(position);
                        blockedUsers.remove(position);
                        adapter.notifyDataSetChanged();
                        databaseReference.child("blockedUsers").child(myUsername).child(username).removeValue();
                    }
                });

                adb.setNegativeButton("No",null);
                adb.show();
            }
        });

    }
}
