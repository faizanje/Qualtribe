package com.example.qualtribe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualtribe.R;
import com.example.qualtribe.models.Order;

import java.util.ArrayList;

public class contract_adapter extends RecyclerView.Adapter<contract_adapter.viewHolder>{

    ArrayList<Order> orders;
    Context context;
    OnOrderClickListener onOrderClickListener;

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener) {
        this.onOrderClickListener = onOrderClickListener;
    }

    public contract_adapter(ArrayList<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    public contract_adapter() {

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.pkg.setText(orders.get(holder.getAdapterPosition()).getPrice());
        holder.pkgdec.setText(orders.get(holder.getAdapterPosition()).getPkgDec());
        holder.req.setText(orders.get(holder.getAdapterPosition()).getRequirements());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onOrderClickListener != null){
                    onOrderClickListener.onOrderClicked(orders.get(holder.getAdapterPosition()));
                }

            }
        });
    }

    public interface OnOrderClickListener{
        void onOrderClicked(Order order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView pkg;
        TextView pkgdec;
        TextView req;
        View view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            pkg =  itemView.findViewById(R.id.orderPKG);
            pkgdec = itemView.findViewById(R.id.orderDECS);
            req = itemView.findViewById(R.id.orderREQ);
            view = itemView;
        }
    }
}
