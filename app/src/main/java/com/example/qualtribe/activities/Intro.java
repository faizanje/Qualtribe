package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.example.qualtribe.activities.Home;
import com.google.firebase.auth.FirebaseAuth;


public class Intro extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getStarted = findViewById(R.id.btnGetStarted);
        getStarted.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(Intro.this, Homepage.class));
//            startActivity(new Intent(Intro.this, Seller_Home.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGetStarted:
                    startActivity(new Intent(this, Home.class));
                    break;
             //   }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){

        }
    }
}