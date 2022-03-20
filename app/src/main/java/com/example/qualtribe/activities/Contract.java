package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.OrdersAdapter;
import com.example.qualtribe.adapters.OrdersAdapter1;
import com.example.qualtribe.databinding.ActivityContractBinding;
import com.example.qualtribe.models.OrderStatus;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contract extends AppCompatActivity implements View.OnClickListener {

    ImageView home, message, search, profile;


    ArrayList<Order> submittedOrders = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    String EMAIL;
    FirebaseAuth m;
    String myUserId;
//    String current_Order_state;
//    contract_adapter adapter = new contract_adapter(submittedOrders, this);

    OrdersAdapter adapter;
    OrdersAdapter1 submittedOrdersAdapter;
    ActivityContractBinding binding;
    String currentOrderMode = OrderStatus.ACTIVE.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        m = FirebaseAuth.getInstance();
        myUserId = FirebaseAuth.getInstance().getUid();
//        current_Order_state = "active";
        EMAIL = m.getCurrentUser().getEmail();
        Log.i("WISHA", "onCreate: 00" + EMAIL);
        setUpRecyclerView();
        getData();


        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        adapter = new OrdersAdapter(this, orders);
        submittedOrdersAdapter = new OrdersAdapter1(this, orders);

        binding.swipeRefreshLayout.setRefreshing(true);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(Contract.this, "Tab Selected: " + tab.getPosition(), Toast.LENGTH_SHORT).show();
                binding.swipeRefreshLayout.setRefreshing(true);
                switch (tab.getPosition()) {
                    case 0:
                        currentOrderMode = OrderStatus.ACTIVE.toString();

                        break;
                    case 1:
                        currentOrderMode = OrderStatus.DELIVERED.toString();
                        break;
                    case 2:
                        currentOrderMode = OrderStatus.REVISION.toString();
                        break;
                    case 3:
                        currentOrderMode = OrderStatus.COMPLETED.toString();
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
//        binding.tabLayout.getTabAt(0).setOnClickListener(v -> {
//            currentOrderMode = OrderStatus.ACTIVE.toString();
//            getData();
//        });
//
//        binding.tabLayout.getChildAt(1).setOnClickListener(v -> {
//            currentOrderMode = OrderStatus.DELIVERED.toString();
//            getSubmittedOrdersData();
//        });
//        binding.tabLayout.getChildAt(2).setOnClickListener(v -> {
//            currentOrderMode = OrderStatus.REVISION.toString();
//            getSubmittedOrdersData();
//        });
//
//        binding.tabLayout.getChildAt(3).setOnClickListener(v -> {
//            currentOrderMode = OrderStatus.COMPLETED.toString();
//            getSubmittedOrdersData();
//        });


    }

    public void getData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("orders");

        binding.swipeRefreshLayout.setRefreshing(true);
        orders.clear();

        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot seller : snapshot.getChildren()) {
                    Order o = seller.getValue(Order.class);
                    if (o.getEmail().equals(EMAIL)) {
                        if (o.getOrderStatus() != null) {
                            if (o.getOrderStatus().equals(currentOrderMode)) {
                                orders.add(o);
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.orderRec.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void updateOrderClickListener() {
        if(currentOrderMode.equals(OrderStatus.ACTIVE.toString())){
            adapter.setOnOrdersClickListener(null);
        }else if(currentOrderMode.equals(OrderStatus.DELIVERED.toString())){
            adapter.setOnOrdersClickListener(new OrdersAdapter.OnOrdersClickListener() {
                @Override
                public void onOrdersClicked(int position, Order submittedOrders) {
                    Intent i = new Intent(Contract.this, Modification.class);
                    i.putExtra(Constants.KEY_ORDER, submittedOrders);
                    startActivity(i);
                }
            });
        }else if(currentOrderMode.equals(OrderStatus.REVISION.toString())){
            adapter.setOnOrdersClickListener(null);
        }else if(currentOrderMode.equals(OrderStatus.COMPLETED.toString())){
            adapter.setOnOrdersClickListener(null);
        }

    }

    public void getSubmittedOrdersData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("orders");


        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Contract.this, "OnDataChange", Toast.LENGTH_SHORT).show();
                for (DataSnapshot seller : snapshot.getChildren()) {
                    orders.clear();
                    Order o = seller.getValue(Order.class);
                    if (o.getBuyerId().equals(FirebaseAuth.getInstance().getUid())) {
                        if (o.getOrderStatus() != null) {
                            if (o.getOrderStatus().equals(currentOrderMode)) {
                                orders.add(o);
                            }
                        }
                    }
                }
                binding.swipeRefreshLayout.setRefreshing(false);

                submittedOrdersAdapter.notifyDataSetChanged();
                binding.orderRec.setAdapter(submittedOrdersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Contract.this, "Cancelled", Toast.LENGTH_SHORT).show();
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

            case R.id.profile_ic:
                startActivity(new Intent(this, loginMenu.class));
                break;

        }
    }
}