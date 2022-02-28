package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qualtribe.R;

public class Search extends AppCompatActivity implements View.OnClickListener  {
    ImageView home, message, order, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

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
            case R.id.home_ic:
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, Please_Login.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, buyer_menu.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Please_Login.class));
                break;

        }

    }
}