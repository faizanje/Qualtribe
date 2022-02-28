package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qualtribe.R;

public class seller_complete_order extends AppCompatActivity implements View.OnClickListener{

    CardView active;
    ImageView home, message, order, profile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_complete_order);

        active = findViewById(R.id.cardView6);
        active.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cardView6:
                startActivity(new Intent(this, seller_order.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, sellerchat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, seller_order.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, sellermenu.class));
                break;

            case R.id.home_ic:
                startActivity(new Intent(this, Seller_Home.class));
                break;

        }
    }
}