package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.databinding.ActivityReviewBinding;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.OrderStatus;
import com.example.qualtribe.utils.Constants;
import com.google.firebase.database.FirebaseDatabase;

public class Review extends AppCompatActivity implements View.OnClickListener {


    ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Order order = (Order) getIntent().getSerializableExtra(Constants.KEY_ORDER);
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = binding.ratingBar2.getRating();
                String feedback = binding.lEmail.getText().toString();
                order.setFeedback(feedback);
                order.setRating(rating);
                order.setOrderStatus(OrderStatus.COMPLETED.toString());
                FirebaseDatabase.getInstance().getReference()
                        .child("orders")
                        .child(order.getOrderId())
                        .setValue(order);
                startActivity(new Intent(Review.this, Homepage.class));
                finish();
                Toast.makeText(Review.this, "Order feedback submitted", Toast.LENGTH_SHORT).show();

            }
        });

//        review = findViewById(R.id.review);
//        review.setOnClickListener(this);
//
//        home = findViewById(R.id.home_ic);
//        home.setOnClickListener(this);
//
//        message = findViewById(R.id.msg_ic);
//        message.setOnClickListener(this);
//
//        search = findViewById(R.id.search_ic);
//        search.setOnClickListener(this);
//
//        profile = findViewById(R.id.profile_ic);
//        profile.setOnClickListener(this);
//
//        order = findViewById(R.id.order_ic);
//        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.review:
//                startActivity(new Intent(this, Homepage.class));
//                break;

            case R.id.home_ic:
                startActivity(new Intent(this, Homepage.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, chat.class));
                break;

            case R.id.search_ic:
                startActivity(new Intent(this, loginSearch.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Contract.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, loginMenu.class));
                finish();

        }

    }
}