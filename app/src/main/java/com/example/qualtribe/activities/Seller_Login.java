package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Seller_Login extends AppCompatActivity implements View.OnClickListener {
    TextView addEmail, addPassword;
    Button addLogin;
    ProgressBar prBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__login);

        mAuth = FirebaseAuth.getInstance();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//// User is signed in
//            startActivity(new Intent(Seller_Login.this, Seller_Home.class));
//            finish();
//        }

        addEmail = findViewById(R.id.lEmail);
        addPassword = findViewById(R.id.lPassword);

        addLogin = findViewById(R.id.lLogin);
        addLogin.setOnClickListener(this);

        prBar = findViewById(R.id.lPr_Bar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLogin:
                loginPage();
                break;
        }

    }

    private void loginPage() {

        String email = addEmail.getText().toString().trim();
        String password = addPassword.getText().toString().trim();

        if (email.isEmpty()) {
            addEmail.setError("Email Address is Required!");
            addEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            addPassword.setError("Password is Required!");
            addPassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            addEmail.setError("Please provide valid email!");
            addEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            addPassword.setError("Password length must be greater than 6");
            addPassword.requestFocus();
            return;
        }
        prBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkSellerExistence();
                } else {
                    prBar.setVisibility(View.GONE);
                    Toast.makeText(Seller_Login.this, "Failed to login. Please check your credentials", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void checkSellerExistence() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference()
                    .child("sellers")
                    .child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d(Constants.TAG, "onDataChange: user exists in seller " + snapshot.exists());
                            if (snapshot.exists()) {
                                startActivity(new Intent(Seller_Login.this, Seller_Home.class));
                                finish();
                            } else {
                                prBar.setVisibility(View.GONE);
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(Seller_Login.this, "Seller doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }

    }
}