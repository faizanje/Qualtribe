package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qualtribe.R;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private View msg;
    private View search;
    private View order;
    private View profile;

    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login_home);
        login.setOnClickListener(this);

        msg = findViewById(R.id.msg_ic);
        msg.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_home:
//                startActivity(new Intent(this, buyer_login.class));
                startActivity(new Intent(this, Seller_buyer_choice.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, Seller_buyer_choice.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Seller_buyer_choice.class));
                break;

            case R.id.search_ic:
                startActivity(new Intent(this, Search.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, buyer_menu.class));
                break;




        }
        }
    }
