package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.chinchillas.chinchillachat.datamodel.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ChatActivity displays a chat for the user. Messages can be added to a chat by this user or by
 * other users in the chat, and the data should be shown upon re-entering the chat.
 *
 * Source: https://www.codeproject.com/Tips/897826/Designing-Android-Chat-Bubble-Chat-UI
 */

public class ChatActivity extends ChinchillaChatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private List<Message> chatLog;
    private FirebaseAuth firebaseAuth;
    private String usernameForMe;
    private DatabaseReference chatThreadReference; // chat thread reference including members and messages lists
    private DatabaseReference chatThreadMessagesReference; // chat thread reference for just messages list
    private String chatThreadID;
    private List<String> friendUsernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatThreadID = getIntent().getStringExtra("chatThreadID");
        friendUsernames = getIntent().getStringArrayListExtra("friendUsernames");
        setContentView(R.layout.activity_chatscreen);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        this.usernameForMe = myUsername;

        initControls();
    }

    /**
     * Initialize controls
     */
    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messages_container);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        chatLog = new ArrayList<Message>();

        // IF CHAT DOES NOT EXIST YET, CREATE A NEW CHAT BASED ON THE MEMBERS TO BE INCLUDED
        if(chatThreadID == null) {
            final ArrayList<String> chatMembersList = new ArrayList<>(friendUsernames);
            chatMembersList.add(myUsername);
            chatThreadReference = databaseReference.child("chats").push();
            chatThreadID = chatThreadReference.getKey();
            HashMap<String, Object> chatMap = new HashMap<>(); // include list of chat members and list of chat messages
            chatMap.put("members", chatMembersList);
            chatMap.put("messages", chatLog);
            chatThreadReference.setValue(chatMap);
            for(String name : chatMembersList){ // add list of other chat members
                ArrayList<String> otherChatMembersList = (ArrayList<String>) chatMembersList.clone();
                otherChatMembersList.remove(name);
                databaseReference.child("usernames").child(name).child("myChats").child(chatThreadID).setValue(otherChatMembersList).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Unable to make chat with those people.", Toast.LENGTH_LONG).show();
                        failedToCreateChat(chatMembersList);
                        ChatActivity.this.finish();
                    }
                });
            }
        } else { // Chat exists. Reference it.
            chatThreadReference = databaseReference.child("chats").child(chatThreadID);
        }
        chatThreadMessagesReference = chatThreadReference.child("messages");

        // UPDATE CHAT THREAD TO REFLECT DATABASE RECORD
        chatThreadMessagesReference.addChildEventListener(new ChildEventListener() {
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

        // ADAPTER TO DISPLAY MESSAGES APPROPRIATELY
        adapter = new ChatAdapter(ChatActivity.this, chatLog, usernameForMe, friendUsernames);
        messagesContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        scroll();

        // SEND NEW MESSAGE
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                Message chatMessage = new Message(messageText, usernameForMe);
               // chatMessage.setTime(DateFormat.getDateTimeInstance().format(new Date()));

                //chatMessage.setTime(dateAsString);
                messageET.setText("");
//                displayMessage(chatMessage);
                chatThreadMessagesReference.push().setValue(chatMessage);
            }
        });


    }

    /**
     * Display message.
     *
     * @param message
     */
    public void displayMessage(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    /**
     * Scroll.
     */
    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    /**
     * To be called when user began a chat then found out they couldn't chat with at least one
     * member. This should undo the creation of the chat.
     *
     * @param chatMembersList
     */
    private void failedToCreateChat(List<String> chatMembersList){
        for(String name : chatMembersList){
            // remove chat from each member's list of chats
            databaseReference.child("usernames").child(name).child("myChats").child(chatThreadID).removeValue();
        }
        // delete the chat
        databaseReference.child("chats").child(chatThreadID).removeValue();
    }

}
