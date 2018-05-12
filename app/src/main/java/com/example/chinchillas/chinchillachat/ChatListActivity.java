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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;


public class ChatListActivity extends ChinchillaChatActivity {
    private Button newChatBtn;
    private ListView listView;

    private FirebaseAuth firebaseAuth;

    private List<String> chatsByMemberNames;
    private List<String> chatsByID;


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
        chatsByMemberNames = new ArrayList<>();
        chatsByID = new ArrayList<>();

        databaseReference.child("usernames").child(myUsername).child("myChats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    final ArrayList<String> chatnames = (ArrayList<String>) dataSnapshot.getValue();
                    String chatID = dataSnapshot.getKey();
                    chatsByID.add(chatID);
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < chatnames.size() - 1; i++) {
                        str.append(chatnames.get(i) + ", ");
                    }
                    str.append(chatnames.get(chatnames.size() - 1));
                    chatsByMemberNames.add(str.toString());
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatListActivity.this, R.layout.text_view, chatsByMemberNames);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                            intent.putExtra("chatThreadID", chatsByID.get(position));
                            intent.putExtra("friendUsernames", chatnames);
                            startActivity(intent);
                        }
                    });
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            // when the user long presses a chat, a prompt asks if they'd like to delete it
                            AlertDialog.Builder adb = new AlertDialog.Builder(ChatListActivity.this);
                            adb.setTitle("Leave Chat");
                            adb.setMessage("Would you like to leave this chat?");
                            final String chatID = chatsByID.get(position);
                            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    chatsByID.remove(position);
                                    databaseReference.child("usernames").child(myUsername).child("myChats").child(chatID).removeValue();
                                    adapter.notifyDataSetChanged();
                                    // finish because it doesn't remove it from the display for some reason...
                                    ChatListActivity.this.finish();
                                }
                            });
                            adb.setNegativeButton("No",null);
                            adb.show();
                            return true;
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
//                            chatsByMemberNames.add(str);
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatListActivity.this, R.layout.text_view, chatsByMemberNames);
//                        listView.setAdapter(adapter);
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
//                                intent.putExtra("chatThreadID", chatsByMemberNames.get(position));
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
