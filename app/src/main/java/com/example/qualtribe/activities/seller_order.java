package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.OrdersAdapter;
import com.example.qualtribe.adapters.OrdersAdapter1;
import com.example.qualtribe.databinding.ActivitySellerOrderBinding;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.SubmittedOrder;
import com.example.qualtribe.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class seller_order extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, order, profile;
    CardView completed, delivered, revision, active;
    String myUserId;
    ActivitySellerOrderBinding binding;
    ArrayList<Order> orderArrayList;
    ArrayList<SubmittedOrder> orderArrayList1;
    OrdersAdapter adapter;
    OrdersAdapter1 adapter1;
    String current_order_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myUserId = FirebaseAuth.getInstance().getUid();
        binding.swipeRefreshLayout.setRefreshing(true);
        current_order_mode = "active";
        orderArrayList = new ArrayList<>();
        adapter = new OrdersAdapter(this, orderArrayList);
        adapter1 = new OrdersAdapter1(this, orderArrayList1);

        adapter.setOnOrdersClickListener(new OrdersAdapter.OnOrdersClickListener() {
            @Override
            public void onOrdersClicked(int position, Order orders) {
                Intent intent = new Intent(seller_order.this, order_submit.class);
                intent.putExtra(Constants.KEY_ORDER_ID, orders.getOrderId());
                if (orders.getBuyerId() != null) {
                    intent.putExtra(Constants.KEY_BUYER_EMAIL, orders.getBuyerId());
                }
                startActivity(intent);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        getData();

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        active = findViewById(R.id.cardView6);
        active.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        completed = findViewById(R.id.cardView5);
        completed.setOnClickListener(this);

        delivered = findViewById(R.id.cardView7);
        delivered.setOnClickListener(this);

        revision = findViewById(R.id.cardView8);
        revision.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);


    }

    private void getData() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        orderArrayList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Order order = child.getValue(Order.class);
                            if (order.getSellerId().equals(myUserId)) {
                                if (order.getStatus().equals(current_order_mode)) {
                                    orderArrayList.add(order);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }




    private void getData1() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("submitted-orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        orderArrayList1.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            SubmittedOrder order = child.getValue(SubmittedOrder.class);
                            if (order.getSellerID().equals(myUserId)) {
                                if (order.getStatus().equals(current_order_mode)) {
                                    orderArrayList1.add(order);
                                }
                            }
                        }
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.msg_ic:
                startActivity(new Intent(this, sellerchat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, seller_order.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, sellermenu.class));
                break;

            case R.id.cardView5:
                current_order_mode = "completed";
                getData();

            case R.id.cardView7:
                current_order_mode = "delivered";
                getData1();
                break;


            case R.id.cardView8:
                current_order_mode = "revision";
                getData();

            case R.id.cardView6:
                current_order_mode = "active";
                getData();

            case R.id.home_ic:
                startActivity(new Intent(this, Seller_Home.class));
                break;


        }
    }
}