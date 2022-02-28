package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.google.firebase.auth.FirebaseAuth;

public class loginMenu extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, order;
    TextView userProfile, logout;
    CardView about, services, team, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        userProfile = findViewById(R.id.profile);
        userProfile.setOnClickListener(this);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        about = findViewById(R.id.About);
        about.setOnClickListener(this);

        services = findViewById(R.id.Services);
        services.setOnClickListener(this);

        team = findViewById(R.id.Team);
        team.setOnClickListener(this);

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
                startActivity(new Intent(this, Homepage.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, chat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Contract.class));
                break;

            case R.id.search_ic:
                startActivity(new Intent(this, loginSearch.class));
                break;

            case R.id.About:
                startActivity(new Intent(this, loginAbout.class));
                break;

            case R.id.Services:
                startActivity(new Intent(this, loginServices.class));
                break;

            case R.id.Team:
                startActivity(new Intent(this, loginTeam.class));
                break;

            case R.id.Contact:
                startActivity(new Intent(this, loginContact.class));
                break;




        }

    }
}