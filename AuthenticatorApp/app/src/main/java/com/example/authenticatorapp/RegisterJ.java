package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterJ extends AppCompatActivity {
        EditText mFullName,mEmail,mPassword,mPhone;
        Button mRegisterBtn;
        TextView mLoginBtn;
        FirebaseAuth fAuth;
        ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_j);

        mFullName = findViewById(R.id.editTextTextPersonName)
        mEmail = findViewById()
    }
}