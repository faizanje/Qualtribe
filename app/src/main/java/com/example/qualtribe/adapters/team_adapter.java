package com.example.qualtribe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualtribe.R;
import com.example.qualtribe.activities.Seller_Profile;
import com.example.qualtribe.models.Sellers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class team_adapter extends RecyclerView.Adapter<team_adapter.viewHolder>{
    ArrayList<Sellers> members;
    Context context;



    public team_adapter(ArrayList<Sellers> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public team_adapter() {

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);

        return new team_adapter.viewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.name.setText(members.get(position).getName());
        holder.services.setText(members.get(position).getService());

        String imageUri = members.get(position).getImg();
        Picasso.with(context).load(imageUri).into(holder.img);

        holder.team_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, Seller_Profile.class);
                myIntent.putExtra("EMAIL", members.get(position).email);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView services;
        ImageView img;

        CardView team_member;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.member_name);
            services = itemView.findViewById(R.id.member_service);
            team_member = itemView.findViewById(R.id.cardContainer_team);
            img = itemView.findViewById(R.id.member_img);

        }
    }
}
