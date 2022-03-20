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
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.OrdersAdapter;
import com.example.qualtribe.databinding.ActivitySellerOrderBinding;
import com.example.qualtribe.models.OrderStatus;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.utils.Constants;
import com.google.android.material.tabs.TabLayout;
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
    //    ArrayList<SubmittedOrder> orderArrayList1;
    OrdersAdapter adapter;
    //    OrdersAdapter1 adapter1;
    String current_order_mode = OrderStatus.ACTIVE.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myUserId = FirebaseAuth.getInstance().getUid();
        binding.swipeRefreshLayout.setRefreshing(true);

        orderArrayList = new ArrayList<>();
//        orderArrayList1 = new ArrayList<>();
        adapter = new OrdersAdapter(this, orderArrayList);
//        adapter1 = new OrdersAdapter1(this, orderArrayList1);

        adapter.setOnOrdersClickListener(new OrdersAdapter.OnOrdersClickListener() {
            @Override
            public void onOrdersClicked(int position, Order submittedOrders) {
                Intent intent = new Intent(seller_order.this, order_submit.class);
                intent.putExtra(Constants.KEY_ORDER, submittedOrders);
                intent.putExtra(Constants.KEY_ORDER_ID, submittedOrders.getOrderId());
                if (submittedOrders.getBuyerId() != null) {
                    intent.putExtra(Constants.KEY_BUYER_EMAIL, submittedOrders.getBuyerId());
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

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(seller_order.this, "Tab Selected: " + tab.getPosition(), Toast.LENGTH_SHORT).show();
                switch (tab.getPosition()) {
                    case 0:
                        current_order_mode = OrderStatus.ACTIVE.toString();
                        break;
                    case 1:
                        current_order_mode = OrderStatus.DELIVERED.toString();
                        break;
                    case 2:
                        current_order_mode = OrderStatus.REVISION.toString();
                        break;
                    case 3:
                        current_order_mode = OrderStatus.COMPLETED.toString();
                        break;
                }

                getData();
                updateOrderClickListener();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        active = findViewById(R.id.cardView6);
//        active.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

//        completed = findViewById(R.id.cardView5);
//        completed.setOnClickListener(this);

//        delivered = findViewById(R.id.cardView7);
//        delivered.setOnClickListener(this);

//        revision = findViewById(R.id.cardView8);
//        revision.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);


    }

    private void updateOrderClickListener() {
        if (current_order_mode.equals(OrderStatus.ACTIVE.toString())
                || current_order_mode.equals(OrderStatus.REVISION.toString())) {
            adapter.setOnOrdersClickListener(new OrdersAdapter.OnOrdersClickListener() {
                @Override
                public void onOrdersClicked(int position, Order submittedOrders) {
                    Intent intent = new Intent(seller_order.this, order_submit.class);
                    intent.putExtra(Constants.KEY_ORDER, submittedOrders);
                    intent.putExtra(Constants.KEY_ORDER_ID, submittedOrders.getOrderId());
                    if (submittedOrders.getBuyerId() != null) {
                        intent.putExtra(Constants.KEY_BUYER_EMAIL, submittedOrders.getBuyerId());
                    }
                    startActivity(intent);
                }
            });
        } else if (current_order_mode.equals(OrderStatus.DELIVERED.toString())) {
            adapter.setOnOrdersClickListener(null);
        } else if (current_order_mode.equals(OrderStatus.COMPLETED.toString())) {
            adapter.setOnOrdersClickListener(null);
        }

    }

    private void getData() {
        binding.swipeRefreshLayout.setRefreshing(true);
        orderArrayList.clear();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Order order = child.getValue(Order.class);
                            if (order.getSellerId().equals(myUserId)) {
                                if (order.getOrderStatus() != null) {
                                    if (order.getOrderStatus().equals(current_order_mode)) {
                                        orderArrayList.add(order);
                                    }
                                }

                            }
                        }
                        adapter.notifyDataSetChanged();
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
    }


//    private void getData1() {
//        FirebaseDatabase.getInstance()
//                .getReference()
//                .child("orders")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        binding.swipeRefreshLayout.setRefreshing(false);
//                        orderArrayList1.clear();
//                        for (DataSnapshot child : snapshot.getChildren()) {
//                            Order order = child.getValue(Order.class);
//                            if (order.getSellerID().equals(myUserId)) {
//                                if (order.getStatus().equals(current_order_mode)) {
//                                    orderArrayList1.add(order);
//                                }
//                            }
//                        }
//                        adapter1.notifyDataSetChanged();
//                        binding.recyclerView.setAdapter(adapter1);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        binding.swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//    }


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
                current_order_mode = OrderStatus.COMPLETED.toString();
                binding.swipeRefreshLayout.setRefreshing(true);
                getData();
                break;


//            case R.id.cardView7:
//                current_order_mode = OrderStatus.DELIVERED.toString();
//                binding.swipeRefreshLayout.setRefreshing(true);
//                getData();
//                break;
//
//
//            case R.id.cardView8:
//                current_order_mode = OrderStatus.REVISION.toString();
//                binding.swipeRefreshLayout.setRefreshing(true);
//                getData();
//                break;
//
//
//            case R.id.cardView6:
//                current_order_mode = OrderStatus.ACTIVE.toString();
//                binding.swipeRefreshLayout.setRefreshing(true);
//                getData();
//                break;


            case R.id.home_ic:
                startActivity(new Intent(this, Seller_Home.class));
                break;


        }
    }
}