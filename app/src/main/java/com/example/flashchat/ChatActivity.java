package com.example.flashchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.security.auth.callback.CallbackHandler;


public class ChatActivity extends AppCompatActivity {

    RecyclerView mChatList;
    EditText mTypeMessage;
    ImageButton mSendButton;
    private String mDisplayName;
    private DatabaseReference mDataBaseReference;
    private ArrayList<InstantMessage> mArrayList;
    theChatListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        setupDisplayName();

        mChatList = findViewById(R.id.listViewID);
        mChatList.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<InstantMessage>();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");

        mDataBaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot DataSnapshot1: dataSnapshot.getChildren()){

                    InstantMessage p = DataSnapshot1.getValue(InstantMessage.class);
                    mArrayList.add(p);
                }
                mAdapter = new theChatListAdapter(ChatActivity.this,mArrayList);
                mChatList.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this,"OPPS...something went wrong",Toast.LENGTH_SHORT);


            }
        });





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
        Log.d("flashchat","The message is  "+inputMessage);
        if(!inputMessage.equals("")){
            InstantMessage chat = new InstantMessage(inputMessage,mDisplayName);
            mDataBaseReference = FirebaseDatabase.getInstance().getReference();
            mDataBaseReference.child("Messages").push().setValue(chat);
            mTypeMessage.setText("");


        }
    }




}
