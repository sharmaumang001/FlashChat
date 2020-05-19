package com.example.flashchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText mEmailView,mPasswordView;
    Button mSignInButton,mRegisterButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = findViewById(R.id.emailId);
        mPasswordView = findViewById(R.id.passwordID);
        mSignInButton = findViewById(R.id.signInID);
        mRegisterButton= findViewById(R.id.registerID);

    }

    public void signInExistingUser(View V ){

    }

    public void registerNewUser (View V ){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }


}
