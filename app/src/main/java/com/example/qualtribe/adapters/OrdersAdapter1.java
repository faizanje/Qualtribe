package com.example.qualtribe.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qualtribe.databinding.LayoutOrdersItemBinding;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.SubmittedOrder;
import com.google.firebase.database.annotations.NotNull;


import java.util.ArrayList;


public class OrdersAdapter1 extends RecyclerView.Adapter<OrdersAdapter1.MyViewHolder> {

    ArrayList<SubmittedOrder> ordersArrayList;
    Context context;
    OnOrdersClickListener onOrdersClickListener;

    public OrdersAdapter1(Context context, ArrayList<SubmittedOrder> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    public void setOnOrdersClickListener(OnOrdersClickListener onOrdersClickListener) {
        this.onOrdersClickListener = onOrdersClickListener;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutOrdersItemBinding binding = LayoutOrdersItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        SubmittedOrder orders = ordersArrayList.get(holder.getAdapterPosition());

        holder.binding.tvRequirement.setText(orders.getRequirements());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrdersClickListener != null) {
                    onOrdersClickListener.onOrdersClicked(holder.getAdapterPosition(), ordersArrayList.get(holder.getAdapterPosition()));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public interface OnOrdersClickListener {
        void onOrdersClicked(int position, SubmittedOrder orders);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LayoutOrdersItemBinding binding;

        public MyViewHolder(@NotNull LayoutOrdersItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}