package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.example.qualtribe.models.Buyers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView email, name, password, profile;
    Button btn_logout, update;
    String user_email;
    int ind, ind1;
    Buyers b1;
    String key, match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.buyer_name);
        email = findViewById(R.id.buyer_email);
        password = findViewById(R.id.buyer_password);
        profile = findViewById(R.id.profile_name);
        update = findViewById(R.id.update_buyer);


        ind1 = 0;
        match = "false";

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = firebaseDatabase.getReference("buyers/");

                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot buy: snapshot.getChildren()) {
                            Buyers b = buy.getValue(Buyers.class);
                            if (b.getEmail().equals(user_email) && ind1 == 0){
                                ind1 = ind1 + 1;
                                b1 = b;
                                match = "true";
                                key = buy.getKey();
                                break;
                            }

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if(match.equals("true")){
                    String nam = name.getText().toString();
                    String ema = email.getText().toString();
                    String pass = password.getText().toString();
                    String img = "xyz";

                    Buyers new_b = new Buyers(nam, ema, pass, img);
                    myRef1.child(key).removeValue();
                    myRef1.push().setValue(new_b);
                    Intent intent = new Intent(Profile.this, Services.class);
                    startActivity(intent);
                }

            }
        });


        user_email = firebaseAuth.getCurrentUser().getEmail();
        ind = 0;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = firebaseDatabase.getReference("buyers/");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot buy: snapshot.getChildren()) {
                    Buyers b = buy.getValue(Buyers.class);
                    if (b.getEmail().equals(user_email) && ind == 0){
                        ind = ind + 1;
                        b1 = b;
                        email.setText(b1.getEmail());
                        password.setText(b1.getPassword());
                        name.setText(b1.getName());
                        profile.setText(b1.getName());
                        Log.i("wish", "onDataChange: "+ b.toString());
                        break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myReference = firebaseDatabase.getReference("buyers");
//
//        myReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot seller: snapshot.getChildren()){
//                    Buyers b = seller.getValue(Buyers.class);
//
////                    if(b.getEmail().equals(user_email) && index == 0){
////                        index = index+1;
////                        email.setText(b.getEmail());
////                        password.setText(b.getPassword());
////                        name.setText(b.getName());
////                        profile.setText(b.getName());
////
////                        break;
////
////
////                    }
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        btn_logout=findViewById(R.id.btnLogout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Profile.this, Home.class);
                Profile.this.startActivity(intent);
                finish();
            }
        });
    }
}