package com.example.flashchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    ListView mChatList;
    EditText mTypeMessage;
    ImageButton mSendButton;
    private String mDisplayName;
    private DatabaseReference mDataBaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        setupDisplayName();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        mChatList = findViewById(R.id.listViewID);
        mTypeMessage = findViewById(R.id.messageId);
        mSendButton = findViewById(R.id.sendButtonId);

        mTypeMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    public void setupDisplayName(){
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);

        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);

        if(mDisplayName == null){
            mDisplayName = "Anonymous";
        }

    }

    public void sendMessage(){

        String inputMessage = mTypeMessage.getText().toString();
        Log.d("flashchat","themessage is  "+inputMessage);
        if(!inputMessage.equals("")){
            InstantMessage chat = new InstantMessage(inputMessage,mDisplayName);
            mDataBaseReference = FirebaseDatabase.getInstance().getReference();
            mDataBaseReference.child("Messages").push().setValue(chat);
            mTypeMessage.setText("");
        }
    }




}
