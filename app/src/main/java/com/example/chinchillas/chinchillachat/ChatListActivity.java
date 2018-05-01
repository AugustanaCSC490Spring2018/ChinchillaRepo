package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ChatListActivity extends ChinchillaChatActivity {
    private Button newChatBtn;
    private ListView listView;

    private FirebaseAuth firebaseAuth;

    private List<String> chats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_chatlist);

        newChatBtn = findViewById(R.id.newChatBtn);
        newChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewChatActivity.class));
            }
        });


        listView = findViewById(R.id.listChats);
        // TODO: ADD CODE TO FILL SCROLLVIEW WITH CURRENT CHATS
        chats = new ArrayList<>();

        databaseReference.child("usernames").child(username).child("myChats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    String str = dataSnapshot.getValue(String.class);
                    chats.add(str);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatListActivity.this, R.layout.text_view, chats);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ChatListActivity.this, ChatActivityTest.class);
                            intent.putExtra("chatThreadID", chats.get(position));
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        databaseReference.child("users").child(firebaseAuth.getUid()).child("myChats").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot != null) {
//                    HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
//                    if (map != null) {
//                        for (String str : map.values()) {
//                            chats.add(str);
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatListActivity.this, R.layout.text_view, chats);
//                        listView.setAdapter(adapter);
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent(ChatListActivity.this, ChatActivityTest.class);
//                                intent.putExtra("chatThreadID", chats.get(position));
//                                startActivity(intent);
//                            }
//                        });
//                    }
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

}
