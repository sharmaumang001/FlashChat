package com.example.flashchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public AutoCompleteTextView mRegisterEmailView,mRegisterUserNameView;
    public EditText mRegiterPasswordView,mConfirmPasswordView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterUserNameView = findViewById(R.id.usernameId);
        mRegisterEmailView= findViewById(R.id.emailRegisterId);
        mRegiterPasswordView= findViewById(R.id.passwordRegisterId);
        mConfirmPasswordView = findViewById(R.id.passwordConfirmId);


         mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });


        mAuth = FirebaseAuth.getInstance();

    }

    public void SignUp (View V){
        attemptRegistration();
    }


    public void attemptRegistration(){

        mRegisterEmailView.setError(null);
        mRegiterPasswordView.setError(null);

        String mEmail = mRegisterEmailView.getText().toString();
        String mPassword = mRegiterPasswordView.getText().toString();

        Log.d("flashchat","The Email is "+mEmail);

        boolean cancel = false;
        View mfocusView= null;

        if(!TextUtils.isEmpty(mPassword) && (!isPasswordValid(mPassword))){
            mRegiterPasswordView.setError("Password is too short or does not match");
            mfocusView = mRegiterPasswordView;
            cancel = true;
        }
        if(TextUtils.isEmpty(mEmail)){
            mRegisterEmailView.setError("The Field is required");
            mfocusView = mRegisterEmailView;
            cancel= true;
        }else if(!isEmailValid(mEmail)){
            mRegisterEmailView.setError("The Email is invalid ");
            mfocusView = mRegisterEmailView;
            cancel= true;
        }

        if(cancel){
            mfocusView.requestFocus();
        }else{
            createFireBaseUser();
        }
    }


    public boolean isEmailValid(String email){

        return email.contains("@");
    }


    public boolean isPasswordValid(String password){
       String mcomfirmedPassword =  mConfirmPasswordView.getText().toString();
       return mcomfirmedPassword.equals(password) && password.length()>7;
    }


    public void createFireBaseUser(){

        String email = mRegisterEmailView.getText().toString();
        String password = mRegiterPasswordView.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("flashchat", "Create user Completed" + task.isSuccessful());

                if(!task.isSuccessful()){

                    Log.d("flashchat","Create user Not Complted ");
                    showErrorDialog("Registration Attempt failed!!!");
                    showErrorDialog("There was problem in signing in!");

                }else {

                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

    }

    public void saveDisplayName(){
        String userName = mRegisterEmailView.getText().toString();
        SharedPreferences prefs = getSharedPreferences("ChatPrefs",0);
        prefs.edit().putString("DISPLAY _NAME_KEY",userName).apply();
    }


    public void showErrorDialog(String messase) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(messase)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.mipmap.ic_warning_icon)
                .show();
    }

}
