package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.models.User;
import com.example.qualtribe.models.Buyers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class buyer_signup extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;


    TextView editName, editEmail, editPassword, editConfirmPassword, editLogin;
    ImageView editImage;
    Button editSignUp;
    ProgressBar prBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_signup);

        mAuth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.suName);
        editEmail = findViewById(R.id.suEmailAddress);
        editPassword = findViewById(R.id.suPassword);
        editConfirmPassword = findViewById(R.id.suConfirmPassword);
        editImage = findViewById(R.id.buyer_image);

        editSignUp = findViewById(R.id.btn_SignUp);
        editSignUp.setOnClickListener(this);

        editLogin = findViewById(R.id.btn_Login);
        editLogin.setOnClickListener(this);

        prBar = findViewById(R.id.Spr_bar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SignUp:
                signUp();
                break;

            case R.id.btn_Login:
                startActivity(new Intent(this, buyer_login.class));
                finish();
        }

    }

    private void signUp() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();




        if(name.isEmpty()){
            editEmail.setError("Full Name is Required!");
            editEmail.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editEmail.setError("Email Address is Required!");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password is Required!");
            editPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            editConfirmPassword.setError("Please Re-enter the Password!");
            editConfirmPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            return;
        }


        if(password.length() < 6){
            editPassword.setError("Password length must be greater than 6");
            editPassword.requestFocus();
            return;
        }


       /* if(password != confirmPassword){
            editConfirmPassword.setError("Please Re-enter the correct password");
            editConfirmPassword.requestFocus();
            return;
        }

        */

        prBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Buyers buyers = new Buyers(name, email, password, "xyz");

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myReference = firebaseDatabase.getReference("buyers/");
                            myReference.push().setValue(buyers);

                            User user = new User(email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        prBar.setVisibility(View.VISIBLE);
                                        Toast.makeText(buyer_signup.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        prBar.setVisibility(View.GONE);
                                        startActivity(new Intent(buyer_signup.this, Homepage.class));

                                    }
                                    else{
                                        Toast.makeText(buyer_signup.this, -5.+"Failed to registered. Try Again!", Toast.LENGTH_LONG).show();
                                        prBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(buyer_signup.this, "User already exists", Toast.LENGTH_LONG).show();
                            prBar.setVisibility(View.GONE);

                        }

                    }
                });



    }
}