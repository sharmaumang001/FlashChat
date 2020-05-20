package com.example.flashchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    AutoCompleteTextView mRegiterEmailView,mRegisterUserNameView;
    EditText mRegiterPasswordView,mConfirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterUserNameView = findViewById(R.id.usernameId);
        mRegiterEmailView= findViewById(R.id.emailRegisterId);
        mRegiterPasswordView= findViewById(R.id.passwordRegisterId);
        mConfirmPasswordView = findViewById(R.id.passwordConfirmId);

    }

    public void SignUp (View V){
        attemptRegistration();
    }

    public void attemptRegistration(){

        mRegiterEmailView.setError(null);
        mRegiterPasswordView.setError(null);

        String mEmail = mRegiterEmailView.getText().toString();
        String mPassword = mRegiterPasswordView.getText().toString();

        boolean cancel = false;
        View mfocusView= null;

        if(!TextUtils.isEmpty(mPassword) && (!isPasswordValid(mPassword))){
            mRegiterPasswordView.setError("Password is too short or does not match");
            mfocusView = mRegiterPasswordView;
            cancel = true;
        }
        if(!TextUtils.isEmpty(mEmail)){
            mRegiterEmailView.setError("The Field is required");
            mfocusView = mRegiterEmailView;
            cancel= true;
        }else if(!isEmailValid(mEmail)){
            mRegiterEmailView.setError("The Email is invalid ");
            mfocusView = mRegiterEmailView;
            cancel= true;
        }

        if(cancel){
            mfocusView.requestFocus();
        }else{

        }
    }

    public boolean isEmailValid(String email){

        return email.contains("@");
    }
    public boolean isPasswordValid(String password){

        return true;
    }







}
