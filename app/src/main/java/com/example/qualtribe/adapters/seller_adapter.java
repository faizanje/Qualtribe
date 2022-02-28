package com.example.qualtribe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualtribe.activities.Packages;
import com.example.qualtribe.R;
import com.example.qualtribe.models.Sellers;
import com.example.qualtribe.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class seller_adapter extends RecyclerView.Adapter<seller_adapter.viewHolder> {

    ArrayList<Sellers> sellers; //seller type
    Context context;
    public seller_adapter(ArrayList<Sellers> sellers, Context context) {
        this.sellers = sellers;
        this.context = context;
    }

    public seller_adapter() {

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) { //get data from arrayList and populate item view
        holder.name.setText(sellers.get(position).getName());
        holder.services.setText(sellers.get(position).getService());

        String imageUri = sellers.get(position).getImg();
        Picasso.with(context).load(imageUri).into(holder.img);

        holder.seller_pkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent myIntent = new Intent(context, Packages.class);
                    myIntent.putExtra("EMAIL", sellers.get(position).email);
                    myIntent.putExtra(Constants.KEY_USER_ID, sellers.get(position).userId);
                    context.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder { //find views in ui

        TextView name;
        TextView services;
        Button seller_pkg;
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.seller_name);
            services = itemView.findViewById(R.id.seller_service);
            seller_pkg = itemView.findViewById(R.id.seller_package);
            img = itemView.findViewById(R.id.pkg_img);
        }
    }
}
