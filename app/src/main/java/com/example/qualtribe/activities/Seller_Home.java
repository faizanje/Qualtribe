package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.qualtribe.R;
import com.google.firebase.auth.FirebaseAuth;

public class Seller_Home extends AppCompatActivity implements View.OnClickListener {

    ImageView message, order, profile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__home);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.msg_ic:
                startActivity(new Intent(this, sellerchat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, seller_order.class));
                break;


            case R.id.profile_ic:
                startActivity(new Intent(this, sellermenu.class));
                break;




        }

    }
}