package com.example.chinchillas.chinchillachat;

/**
 * Created by angelicagarcia16 on 4/23/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.chinchillas.chinchillachat.datamodel.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Source: https://www.codeproject.com/Tips/897826/Designing-Android-Chat-Bubble-Chat-UI
 */

public class ChatActivityTest extends ChinchillaChatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private List<Message> chatLog;
    private FirebaseAuth firebaseAuth;
    private String senderIDForMe;
    private DatabaseReference chatThreadReference;
    private String chatThreadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatThreadID = getIntent().getStringExtra("chatThreadID");
        setContentView(R.layout.activity_chattest);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        this.senderIDForMe = firebaseAuth.getCurrentUser().getUid();

        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messages_container);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        chatLog = new ArrayList<Message>();
        if(chatThreadID == null) {
            chatThreadReference = databaseReference.child("chats").push();
            chatThreadID = chatThreadReference.getKey();
            chatThreadReference.setValue(chatLog);
            databaseReference.child("users").child(senderIDForMe).child("myChats").push().setValue(chatThreadID);
        } else {
            chatThreadReference = databaseReference.child("chats").child(chatThreadID);
        }

        chatThreadReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Message message = child.getValue(Message.class);
                    chatLog.add(message);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chatThreadReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                chatLog.add(message);
                adapter.notifyDataSetChanged();
                scroll();
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

        adapter = new ChatAdapter(ChatActivityTest.this, chatLog, senderIDForMe);
        messagesContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        scroll();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                Message chatMessage = new Message(messageText,senderIDForMe);
                messageET.setText("");
//                displayMessage(chatMessage);
                chatThreadReference.push().setValue(chatMessage);
            }
        });


    }

    public void displayMessage(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

//    private void loadDummyHistory() {
//
//                ChatMessage msg = new ChatMessage();
//        msg.setId(1);
//        msg.setMe(false);
//        msg.setMessage("Howdy");
//        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatLog.add(msg);
//        ChatMessage msg1 = new ChatMessage();
//        msg1.setId(2);
//        msg1.setMe(false);
//        msg1.setMessage("How r u doing???");
//        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatLog.add(msg1);
//
//
//        for(int i = 0; i< chatLog.size(); i++) {
//            ChatMessage message = chatLog.get(i);
//            displayMessage(message);
//        }
//    }
}
