package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.models.Sellers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Seller_Profile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    public ArrayList<Sellers> sellers = new ArrayList<>();
    Sellers mySeller = new Sellers();

    TextView pkg1, pkg2, pkg3, pkg_desc, name, intro;
    Button b1, b2, b3;
    CardView c1, c2, c3;
    ImageView img;
    private Button buyer;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__profile);

        pkg1 = findViewById(R.id.pkg1);
        pkg2 = findViewById(R.id.pkg2);
        pkg3 = findViewById(R.id.pkg3);
        name = findViewById(R.id.seller_name);
        intro = findViewById(R.id.seller_intro);
        img = findViewById(R.id.seller_img);


        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        pkg_desc = findViewById(R.id.pkg_det);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);


        buyer = findViewById(R.id.logee);
        buyer.setOnClickListener(this);




        onPopulateData();

        mAuth = FirebaseAuth.getInstance();

        setUpViews();

    }

    public void onPopulateData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("sellers/");
        String email = getIntent().getStringExtra("EMAIL");

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot seller: snapshot.getChildren()){
                    Sellers s = seller.getValue(Sellers.class);
                    if(s.getEmail().equals(email)){
                        mySeller = s;
                        pkg1.setText(mySeller.getPkg1());
                        pkg2.setText(mySeller.getPkg2());
                        pkg3.setText(mySeller.getPkg3());
                        pkg_desc.setText(mySeller.getDesc1());
                        name.setText(mySeller.getName());
                        intro.setText(mySeller.getIntro());
                        String imageUri = mySeller.getImg();
                        Picasso.with(Seller_Profile.this).load(imageUri).into(img);
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkg_desc.setText(mySeller.getDesc1());
                                c1.setBackgroundColor(c1.getContext().getResources().getColor(R.color.grey));
                                c2.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));
                                c3.setBackgroundColor(c3.getContext().getResources().getColor(R.color.white));

                            }
                        });

                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkg_desc.setText(mySeller.getDesc2());
                                c1.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));
                                c2.setBackgroundColor(c2.getContext().getResources().getColor(R.color.grey));
                                c3.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));

                            }
                        });

                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkg_desc.setText(mySeller.getDesc3());
                                c1.setBackgroundColor(c1.getContext().getResources().getColor(R.color.white));
                                c2.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));
                                c3.setBackgroundColor(c3.getContext().getResources().getColor(R.color.grey));

                            }
                        });
                        break;


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUpViews() {

        //setUpDrawerLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logee:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Toast.makeText(Seller_Profile.this, "You are already logged in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Seller_Profile.this, Services.class));

                } else {

                    startActivity(new Intent(this, buyer_login.class));

                    break;
                }
                break;
        }

    }
}