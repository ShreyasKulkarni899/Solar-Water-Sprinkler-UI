package com.nkocet.untitled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Card> cards;

    public RecyclerViewAdapter(Context context, List<Card> cards) {
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
        holder.location.setText(cards.get(position).location);
        holder.cardBody.setCardBackgroundColor(cards.get(position).cardBackgroundColor);
        holder.cardBottom.setBackgroundColor(cards.get(position).cardBottomColor);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, location;
        MaterialCardView cardBody;
        LinearLayout cardBottom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardName);
            location = itemView.findViewById(R.id.cardLocation);
            cardBody = itemView.findViewById(R.id.cardBody);
            cardBottom = itemView.findViewById(R.id.cardBottom);
        }
    }
}