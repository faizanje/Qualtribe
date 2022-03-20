package com.example.qualtribe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.OrderStatus;
import com.example.qualtribe.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceOrder extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, order, profile;

    String PRICE, PKGDESC, EMAIL, sellerID;
    EditText req;
    Button btn;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();
        mauth = FirebaseAuth.getInstance();
        EMAIL = mauth.getCurrentUser().getEmail();
        PRICE = intent.getStringExtra("PRICE");
        PKGDESC = intent.getStringExtra("PKGDESC");
        sellerID = intent.getStringExtra(Constants.KEY_USER_ID);

        Log.i("WISHA", "onCreate: " + PRICE + PKGDESC);

        req = findViewById(R.id.requirements);
        btn = findViewById(R.id.submit_req);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String uniqueID = UUID.randomUUID().toString();
                String r = req.getText().toString();
//                Log.i("WISHA", "onClick: " + order);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = firebaseDatabase.getReference("orders").push();
                Order order = new Order(EMAIL, PRICE, PKGDESC, r, sellerID, myRef1.getKey(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), OrderStatus.ACTIVE.toString());
                myRef1.setValue(order);
                Toast.makeText(PlaceOrder.this, "Order has been placed successfully!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(PlaceOrder.this, Contract.class);
                startActivity(intent1);
                finish();

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

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
                break;

        }

    }
}