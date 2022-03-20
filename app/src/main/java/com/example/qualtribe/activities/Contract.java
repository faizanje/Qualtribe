package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.adapters.OrdersAdapter;
import com.example.qualtribe.adapters.OrdersAdapter1;
import com.example.qualtribe.adapters.contract_adapter;
import com.example.qualtribe.adapters.seller_adapter;
import com.example.qualtribe.databinding.ActivityContractBinding;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.OrderStatus;
import com.example.qualtribe.models.Sellers;
import com.example.qualtribe.models.SubmittedOrder;
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


    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<SubmittedOrder> submittedOrders = new ArrayList<>();
    String EMAIL;
    FirebaseAuth m;
    String myUserId;
//    String current_Order_state;
//    contract_adapter adapter = new contract_adapter(orders, this);

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
        submittedOrdersAdapter = new OrdersAdapter1(this, submittedOrders);

        adapter.setOnOrdersClickListener((position, orders1) -> {
        });
        submittedOrdersAdapter.setOnOrdersClickListener((position, submittedOrder) -> {
            Intent i = new Intent(Contract.this, Modification.class);
            i.putExtra(Constants.KEY_SUBMITTED_ORDER, submittedOrder);
            startActivity(i);
        });
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
                Toast.makeText(Contract.this, "Tab Selected: " + tab.getPosition(), Toast.LENGTH_SHORT).show();
                binding.swipeRefreshLayout.setRefreshing(true);
                switch (tab.getPosition()) {
                    case 0:
                        currentOrderMode = OrderStatus.ACTIVE.toString();
                        getData();
                        break;
                    case 1:
                        currentOrderMode = OrderStatus.DELIVERED.toString();
                        getSubmittedOrdersData();
                        break;
                    case 2:
                        currentOrderMode = OrderStatus.REVISION.toString();
                        getSubmittedOrdersData();
                        break;
                    case 3:
                        currentOrderMode = OrderStatus.COMPLETED.toString();
                        getSubmittedOrdersData();
                        break;


                }
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

        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot seller : snapshot.getChildren()) {
                    orders.clear();
                    Order o = seller.getValue(Order.class);
                    if (o.getEmail().equals(EMAIL)) {
                        if (o.getOrderStatus() != null) {
                            if (o.getOrderStatus().equals(currentOrderMode)) {
                                orders.add(o);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    binding.swipeRefreshLayout.setRefreshing(false);
                    binding.orderRec.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getSubmittedOrdersData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("submitted-orders");


        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Contract.this, "OnDataChange", Toast.LENGTH_SHORT).show();
                for (DataSnapshot seller : snapshot.getChildren()) {
                    submittedOrders.clear();
                    SubmittedOrder o = seller.getValue(SubmittedOrder.class);
                    if (o.getBuyerEmail().equals(EMAIL)) {
                        if (o.getStatus() != null) {
                            if (o.getStatus().equals(currentOrderMode)) {
                                submittedOrders.add(o);
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