package com.example.flashchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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

        setupDisplayName();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        mChatList=findViewById(R.id.listViewID);
        mTypeMessage=findViewById(R.id.messageId);
        mSendButton=findViewById(R.id.sendButtonId);




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
        Log.d("flashchat","I Sent Something");

        String inputMessage = mTypeMessage.getText().toString();
        if(!inputMessage.equals("")){
            InstantMessage chat = new InstantMessage(inputMessage,mDisplayName);
            mDataBaseReference.child("messages").push().setValue(chat);
            mTypeMessage.setText("");
        }

    }
}
