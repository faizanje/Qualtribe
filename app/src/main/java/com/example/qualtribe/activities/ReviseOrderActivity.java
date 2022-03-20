package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.qualtribe.databinding.ActivityReviseOrderBinding;

public class ReviseOrderActivity extends AppCompatActivity {

    ActivityReviseOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviseOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}