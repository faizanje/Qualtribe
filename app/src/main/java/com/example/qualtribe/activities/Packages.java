package com.example.qualtribe.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.Sellers;
import com.example.qualtribe.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Packages extends AppCompatActivity {

    public ArrayList<Sellers> sellers = new ArrayList<>();
    Sellers mySeller = new Sellers();
    TextView  pkg_desc;
    TextView pkg1, pkg2, pkg3;
    Button b1, b2, b3;
    CardView c1, c2, c3;
    Button ordr;
    TextView pkgD;
    String price;
    String pkgDesc;

    ActionBarDrawerToggle obj;
    NavigationView navigationview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        pkg1 = findViewById(R.id.pkg1);
        pkg2 = findViewById(R.id.pkg2);
        pkg3 = findViewById(R.id.pkg3);

        price = pkg1.getText().toString();

        pkgD = findViewById(R.id.pkg_details);







        ordr = findViewById(R.id.btn_order);


        ordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkgDesc = pkgD.getText().toString();
                Intent intent = new Intent(Packages.this, payment.class);
                intent.putExtra("PRICE", price);
                intent.putExtra("PKGDESC", pkgDesc);
                intent.putExtra(Constants.KEY_USER_ID, getIntent().getStringExtra(Constants.KEY_USER_ID));
                startActivity(intent);

            }
        });



        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        pkg_desc = findViewById(R.id.pkg_details);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);

        onPopulateData();

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
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                price = pkg1.getText().toString();
                                pkg_desc.setText(mySeller.getDesc1());
                                c1.setBackgroundColor(c1.getContext().getResources().getColor(R.color.grey));
                                c2.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));
                                c3.setBackgroundColor(c3.getContext().getResources().getColor(R.color.white));

                            }
                        });

                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                price = pkg2.getText().toString();
                                pkg_desc.setText(mySeller.getDesc2());
                                c1.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));
                                c2.setBackgroundColor(c2.getContext().getResources().getColor(R.color.grey));
                                c3.setBackgroundColor(c2.getContext().getResources().getColor(R.color.white));

                            }
                        });

                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                price = pkg3.getText().toString();
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
/*
    private void setUpDrawerLayout() {

        setSupportActionBar(findViewById(R.id.appBar));
        obj = new ActionBarDrawerToggle(Packages.this, findViewById(R.id.main_drawer), R.string.app_name, R.string.app_name);
        obj.syncState();

        navigationview = findViewById(R.id.navigationView);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Log.i("Jaleel", "onNavigationItemSelected: "+item.getTitle());
                Intent intent=null;
                if(item.getTitle().equals("Contact")){
                    intent=new Intent(Packages.this,Contact.class);
                }
                else if(item.getTitle().equals("Profile")){
                    intent=new Intent(Packages.this,Profile.class);
                }
                else{
                    intent=new Intent(Packages.this,About.class);
                }
                startActivity(intent);
                return true;
            }
        });

    }

 */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (obj.onOptionsItemSelected(item)) {
            return (true);

        }
        return super.onOptionsItemSelected(item);
    }

}