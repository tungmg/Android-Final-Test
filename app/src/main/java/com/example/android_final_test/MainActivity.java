package com.example.android_final_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_final_test.view.SignIn;
import com.example.android_final_test.view.SignUp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    public void init(){
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn){
            Intent signIn = new Intent(this, SignIn.class);
            startActivity(signIn);
        }
        else if(v == btnSignUp){
            Intent signUp = new Intent(this, SignUp.class);
            startActivity(signUp);
        }
    }
}
