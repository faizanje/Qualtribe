package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.contract_adapter;
import com.example.qualtribe.adapters.seller_adapter;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.Sellers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contract extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, profile ;



    ArrayList<Order> orders = new ArrayList<>();
    String EMAIL;
    FirebaseAuth m;
    contract_adapter adapter = new contract_adapter(orders, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        m = FirebaseAuth.getInstance();
        EMAIL = m.getCurrentUser().getEmail();
        Log.i("WISHA", "onCreate: 00" + EMAIL);
        onPopulateData();



        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);


    }

    public void onPopulateData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("orders");

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot seller: snapshot.getChildren()){
                    Order o = seller.getValue(Order.class);
                    if (o.getEmail().equals(EMAIL)) {
                        orders.add(o);
                    }

                }
                setUpRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUpRecyclerView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.orderRec);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
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

            case R.id.profile_ic:
                startActivity(new Intent(this, loginMenu.class));
                break;

        }
    }
}