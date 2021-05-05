package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Card> cards;

    public RecyclerViewAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(cards.get(position).name);
        holder.name.setTextColor(Color.parseColor(cards.get(position).textColor));
        holder.location.setText(cards.get(position).location);
        holder.cardBody.setBackgroundColor(Color.parseColor(cards.get(position).cardBackgroundColor));
        holder.cardBottom.setBackgroundColor(Color.parseColor(cards.get(position).cardBottomColor));
        holder.status.setImageResource(cards.get(position).sprinkler.status == Sprinkler.ONLINE ?
                R.drawable.ic_baseline_online_24
                : R.drawable.ic_baseline_offline_24);

        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("card", cards.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void updateReceiptsList(ArrayList<Card> cards) {
        this.cards = cards;
        this.notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, location;
        RelativeLayout cardBody;
        LinearLayout cardBottom;
        MaterialCardView card;
        ImageView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardName);
            location = itemView.findViewById(R.id.cardLocation);
            card = itemView.findViewById(R.id.card);
            cardBody = itemView.findViewById(R.id.cardBody);
            cardBottom = itemView.findViewById(R.id.cardBottom);
            status = itemView.findViewById(R.id.status);
        }
    }
}