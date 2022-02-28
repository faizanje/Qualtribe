package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.team_adapter;
import com.example.qualtribe.models.Sellers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class loginTeam extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, order, profile;

    FirebaseStorage storage;
    ArrayList<Sellers> members = new ArrayList<>();
    team_adapter adapter = new team_adapter(members, this);
    RecyclerView recyclerView;

    private StorageReference myStorage;
    ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team);
        storage = FirebaseStorage.getInstance();

        onPopulateData();

        myStorage = FirebaseStorage.getInstance().getReference().child("sellers/hanan.jpeg");

        StorageReference listRef = storage.getReference().child("sellers");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            item.getDownloadUrl().addOnSuccessListener(uri -> {
                                Log.i("TAG-Img", "onSuccess: --> " +uri.toString());

                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

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

    private void onPopulateData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("sellers/");

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot seller: snapshot.getChildren()){
                    Sellers s = seller.getValue(Sellers.class);

                    members.add(s);

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
        RecyclerView recyclerView = findViewById(R.id.team_RecyclerView);
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

            case R.id.order_ic:
                startActivity(new Intent(this, Contract.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, loginMenu.class));
                finish();

        }
    }
}