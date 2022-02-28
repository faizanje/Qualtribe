package com.example.qualtribe.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.qualtribe.models.Buyers;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.qualtribe.R;
import com.google.firebase.auth.FirebaseAuth;

public class sellermenu extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, order;
    TextView userProfile, logout;
    CardView about, contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellermenu);



        userProfile = findViewById(R.id.profile);
        userProfile.setOnClickListener(this);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        about = findViewById(R.id.About);
        about.setOnClickListener(this);


        contact = findViewById(R.id.Contact);
        contact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.profile:
                startActivity(new Intent(this, Profile.class));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.home_ic:
                startActivity(new Intent(this, Seller_Home.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, sellerchat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, seller_order.class));
                break;


            case R.id.About:
                startActivity(new Intent(this, seller_about.class));
                break;


            case R.id.Contact:
                startActivity(new Intent(this, seller_contact.class));
                break;




        }

    }
}