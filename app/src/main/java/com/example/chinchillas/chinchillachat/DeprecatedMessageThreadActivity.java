package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chinchillas.chinchillachat.datamodel.Message;
import com.example.chinchillas.chinchillachat.datamodel.MessageThread;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Deprecated. Use ChatListActivity instead.
 */

public class DeprecatedMessageThreadActivity extends ChinchillaChatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText messageBox;
    private ImageButton sendButton;
    private LinearLayout layout;
    private MessageThread messageThread;
    private ScrollView scrollView;

    private String messageThreadID;

    private String partnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pseu = getIntent().getStringExtra("pseudonym");
        final String username = getIntent().getStringExtra("myUsername");
        final String pseudonym = pseu == null ? username : pseu;
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        setContentView(R.layout.activity_messagethread);

        layout = findViewById(R.id.message_thread_layout);

        scrollView = findViewById(R.id.message_thread_scroll_view);

        databaseReference.child("users").child(firebaseAuth.getUid()).child("pseudouser").addValueEventListener(new ValueEventListener() {
            // https://www.captechconsulting.com/blogs/firebase-realtime-database-android-tutorial
            // used as a reference.
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot chats = dataSnapshot.child("chats");
                if (chats != null){
                    DataSnapshot chat = chats.child(username == null ? pseudonym : username);
                    if (chat == null){
                        messageThread = new MessageThread();
                    } else {
                        boolean activityJustLaunched = (messageThread == null);
                        messageThread = chat.getValue(MessageThread.class);
                        if (messageThread == null) {
                            messageThread = new MessageThread();
                        } else {
                            Log.d("CHINCHTAG", "participants: " + messageThread.getParticipants());
                            if (activityJustLaunched) {
                                for (Message message : messageThread.getMessages()) {
                                    addMessageInTextView(message.getMessage());
                                }
                            } else {
                                Message message = messageThread.getMessages().get(messageThread.getMessages().size() - 1);
                                addMessageInTextView(message.getMessage());
                            }
                        }
                    }
                }
                scrollView.fullScroll(View.FOCUS_DOWN);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageBox = findViewById(R.id.chat_text);
        sendButton = findViewById(R.id.send_message_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageBox.getText().toString();
                if(messageText.length()>0) {
                    Message message = new Message(messageText, firebaseAuth.getUid());
                    messageThread.addMessage(message);
                    databaseReference.child("users").child(firebaseAuth.getUid()).child("pseudouser").child("chats").child((username == null ? pseudonym : username)).setValue(messageThread);
                    if (partnerID == null) {
                        databaseReference.child("pseudonymList").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null) {
                                    DataSnapshot ds = dataSnapshot.child(pseudonym);
                                    if (ds != null) {
                                        partnerID = ds.getValue(String.class);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    if (partnerID == null) {
                        partnerID = "kSGxGsmmbfgglSeUXBcyjIPEpv82";
                    }
//                databaseReference.child("users").child("mXjOE2p2ccZPht5NB8XYqQq9Bq22").child("pseudouser").child("chats").child(pref.getString("myUsername", null)).setValue(messageThread);
//                databaseReference.setValue("users/" + partnerID + "/pseudouser/chats/" + pref.getString("myUsername", null), messageThread);

                    messageBox.setText("");
                }
            }
        });
    }

    private void addMessageInTextView(String messageStr) {
        TextView messageTV = new TextView(DeprecatedMessageThreadActivity.this);
        messageTV.setText(messageStr);
        layout.addView(messageTV);
    }



}
