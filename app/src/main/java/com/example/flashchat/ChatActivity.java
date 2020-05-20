package com.example.flashchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ChatActivity extends AppCompatActivity {

    ListView mChatList;
    EditText mTypeMessage;
    ImageButton mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        mChatList=findViewById(R.id.listViewID);
        mTypeMessage=findViewById(R.id.messageId);
        mSendButton=findViewById(R.id.sendButtonId);
    }
}
