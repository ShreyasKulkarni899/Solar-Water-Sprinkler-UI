package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.name.setTextColor(Color.parseColor(cards.get(position).textColor));
        holder.location.setText(cards.get(position).location);
        holder.cardBody.setBackgroundColor(Color.parseColor(cards.get(position).cardBackgroundColor));
        holder.cardBottom.setBackgroundColor(Color.parseColor(cards.get(position).cardBottomColor));

        holder.cardBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("card", cards.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, location;
        RelativeLayout cardBody;
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