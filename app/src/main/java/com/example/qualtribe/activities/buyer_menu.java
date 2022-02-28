package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qualtribe.R;

public class buyer_menu extends AppCompatActivity implements View.OnClickListener {
    TextView signIn, signUp;
    ImageView home, message, search, order;
    CardView about, services, team, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_menu);

        signIn = findViewById(R.id.inSign);
        signIn.setOnClickListener(this);

        signUp = findViewById(R.id.upSign);
        signUp.setOnClickListener(this);

        about = findViewById(R.id.About);
        about.setOnClickListener(this);

        services = findViewById(R.id.Services);
        services.setOnClickListener(this);

        team = findViewById(R.id.Team);
        team.setOnClickListener(this);

        contact = findViewById(R.id.Contact);
        contact.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.inSign:
//                startActivity(new Intent(this, buyer_login.class));
                startActivity(new Intent(this, Seller_buyer_choice.class));
                break;

            case R.id.upSign:
                startActivity(new Intent(this, buyer_signup.class));
                break;

            case R.id.About:
                startActivity(new Intent(this, About.class));
                break;

            case R.id.Services:
                startActivity(new Intent(this, Services.class));
                break;

            case R.id.Team:
                startActivity(new Intent(this, Team.class));
                break;

            case R.id.Contact:
                startActivity(new Intent(this, Contact.class));
                break;

            case R.id.home_ic:
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, Please_Login.class));
                break;

            case R.id.search_ic:
                startActivity(new Intent(this, Search.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Please_Login.class));
                finish();

        }

    }
}