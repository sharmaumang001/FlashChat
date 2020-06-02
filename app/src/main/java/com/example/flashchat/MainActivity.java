package com.example.flashchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    EditText mEmailView,mPasswordView;
    Button mSignInButton,mRegisterButton;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = findViewById(R.id.emailId);
        mPasswordView = findViewById(R.id.passwordID);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    public void signInExistingUser(View V ){
        attemptLogin();
    }

    public void registerNewUser (View V ){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        return;

        Toasty.info(this, "LOGIN IN PROCESS", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Log.d("flashchat","Login attempt unsuccessful"+task.getException());
                    showErrorToUser("Login Attempt Unsuccessful");
                }else {

                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    finish();
                    startActivity((intent));

                }
            }
        });

    }

    public void showErrorToUser(String message){

       new  AlertDialog.Builder(this).setTitle("Oops")
               .setMessage(message)
               .setPositiveButton(android.R.string.ok,null)
               .setIcon(R.mipmap.ic_warning_icon)
               .show();
    }




}
