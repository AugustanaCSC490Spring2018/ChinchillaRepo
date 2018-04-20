package com.example.chinchillas.chinchillachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chinchillas.chinchillachat.datamodel.Message;
import com.example.chinchillas.chinchillachat.datamodel.MessageThread;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageThreadActivity extends ChinchillaChatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText messageBox;
    private ImageButton sendButton;
    private LinearLayout layout;
    private MessageThread messages;
    private ScrollView scrollView;

    private String messageThreadID;

    private String partnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pseu = getIntent().getStringExtra("pseudonym");
        final String username = getIntent().getStringExtra("username");
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
                        messages = new MessageThread();
                    } else {
                        messages = chat.getValue(MessageThread.class);
                        if (messages == null) {
                            messages = new MessageThread();
                        } else {
                            Message message = messages.getMessages().get(messages.getMessages().size() - 1);
                            TextView messageText = new TextView(MessageThreadActivity.this);
                            messageText.setText(message.getMessage());
                            layout.addView(messageText);
                            scrollView.fullScroll(View.FOCUS_DOWN);
//                            for (Message message : messages.getMessages()) {
//                                TextView messageText = new TextView(MessageThreadActivity.this);
//                                messageText.setText(message.getMessage());
//                                layout.addView(messageText);
//                            }
                        }
                    }
                }
//                List<Message> messages = new ArrayList<>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    Message message = ds.getValue(Message.class);
//                    messages.add(message);
//                    TextView messageText = new TextView(MessageThreadActivity.this);
//                    messageText.setText(message.getMessage());
//                    layout.addView(messageText);
//                }

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
                Message message = new Message(messageText, firebaseAuth.getUid());
                messages.addMessage(message);
                databaseReference.child("users").child(firebaseAuth.getUid()).child("pseudouser").child("chats").child((username == null ? pseudonym : username)).setValue(messages);
                if(partnerID == null) {
                    databaseReference.child("pseudonymList").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                DataSnapshot ds = dataSnapshot.child(pseudonym);
                                if(ds != null) {
                                    partnerID = ds.getValue(String.class);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                if(partnerID == null){
                    partnerID = "kSGxGsmmbfgglSeUXBcyjIPEpv82";
                }
//                databaseReference.child("users").child("mXjOE2p2ccZPht5NB8XYqQq9Bq22").child("pseudouser").child("chats").child(pref.getString("myUsername", null)).setValue(messages);
//                databaseReference.setValue("users/" + partnerID + "/pseudouser/chats/" + pref.getString("myUsername", null), messages);

                messageBox.setText("");
            }
        });
    }

}
