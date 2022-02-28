package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.seller_adapter;
import com.example.qualtribe.models.Sellers;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class loginServices extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, order, profile;

    public FirebaseAuth mAuth;
    ActionBarDrawerToggle obj;
    private Button pkg;
    NavigationView navigationview;

    ArrayList<Sellers> sellers = new ArrayList<>(); //array of object
    seller_adapter adapter = new seller_adapter(sellers, this); //pass array to adapter object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_services);
        setUpViews();
        onPopulateData();

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);
    }

    public void onPopulateData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("sellers/");

        //Add new seller through code
//        Sellers seller6 = new Sellers("100$", "250$", "500$",
//                "salman@gmail.com","Salman Ali","App Developer","Desc1","Desc2",
//                "Desc3","intro",
//                "https://firebasestorage.googleapis.com/v0/b/qualtribe.appspot.com/o/sellers%2Fwisha.jpeg?alt=media&token=fdc4aecd-e345-450e-a4af-b6c4b16113a6");
//        myReference.push().setValue(seller6);

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot seller: snapshot.getChildren()){
                    Sellers s = seller.getValue(Sellers.class);

                    sellers.add(s);

                }
                setUpRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUpRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        RecyclerView recyclerView = findViewById(R.id.seller_RecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setUpViews() {

        setUpDrawerLayout();
    }

    private void setUpDrawerLayout() {

        /*setSupportActionBar(findViewById(R.id.appBar));
        obj = new ActionBarDrawerToggle(Services.this, findViewById(R.id.main_drawer), R.string.app_name, R.string.app_name);
        obj.syncState();*/

       /* navigationview = findViewById(R.id.navigationView);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Log.i("Jaleel", "onNavigationItemSelected: "+item.getTitle());
                Intent intent=null;
                if(item.getTitle().equals("Contact")){
                    intent=new Intent(Services.this, Contact.class);
                }
                else if(item.getTitle().equals("Profile")){
                    intent=new Intent(Services.this, Profile.class);
                }

                else if(item.getTitle().equals("Orders")){
                    intent=new Intent(Services.this, Contract.class);
                }
                else{
                    intent=new Intent(Services.this, About.class);
                }
                startActivity(intent);
                return true;
            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (obj.onOptionsItemSelected(item)) {
            return (true);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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