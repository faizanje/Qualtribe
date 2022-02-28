package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qualtribe.R;
import com.example.qualtribe.databinding.ActivitySellerBuyerChoiceBinding;

public class Seller_buyer_choice extends AppCompatActivity {

    ActivitySellerBuyerChoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerBuyerChoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buyer.setOnClickListener(v -> startActivity(new Intent(Seller_buyer_choice.this, buyer_login.class)));
        binding.seller.setOnClickListener(v -> startActivity(new Intent(Seller_buyer_choice.this, Seller_Login.class)));


    }
}