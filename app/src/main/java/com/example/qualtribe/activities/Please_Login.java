package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qualtribe.R;

public class Please_Login extends AppCompatActivity implements View.OnClickListener {

    private Button plz_Login, plz_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_login);

        plz_Login = findViewById(R.id.plz_Login);
        plz_Login.setOnClickListener(this);

        plz_signup = findViewById(R.id.plz_signup);
        plz_signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plz_Login:
                startActivity(new Intent(this, buyer_login.class));
                break;

            case R.id.plz_signup:
                startActivity(new Intent(this, buyer_signup.class));
                break;

        }
    }
}