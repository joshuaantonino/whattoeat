package com.example.whattoeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp<progressDialog> extends AppCompatActivity implements View.OnClickListener {

    private TextView welnew;
    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private Button btSignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageView bckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView already = findViewById(R.id.already);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        welnew = findViewById(R.id.welnew);
        bckButton = findViewById(R.id.bckButton);

        btSignup = findViewById(R.id.btSignup);
        btSignup.setOnClickListener(this);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);

        bckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, WhattoEat.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welnew:
                startActivity(new Intent(this, LogIn.class));
                break;
            case R.id.btSignup:
                btSignup();
        }
    }

    private void btSignup() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String cpassword = inputConfirmPassword.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError("Email is required!");
            inputEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please provide valid email!");
            inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            inputPassword.setError("Password is required!");
            inputPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            inputPassword.setError("Minimum password length should be 6 characters!");
            inputPassword.requestFocus();
            return;
        }

        if (cpassword.isEmpty()) {
            inputConfirmPassword.setError("Confirm password is required!");
            inputConfirmPassword.requestFocus();
            return;
        }
        if (!cpassword.equals(password)) {
            inputConfirmPassword.setError("Password does not matched!");
            return;

        } else {

            inputConfirmPassword.setError("Password Matched!");
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "You've been successfully sign up!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "You have an existing account!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}