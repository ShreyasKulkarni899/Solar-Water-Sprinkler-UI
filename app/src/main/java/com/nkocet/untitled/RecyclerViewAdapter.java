
package com.nkocet.untitled;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    Context context;
    ArrayList<Card> cards;
    CardClickListener listener;
    Database database;

    public RecyclerViewAdapter(Context context, ArrayList<Card> cards, CardClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.cards = cards;
        database = new Database(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(cards.get(position).name);
        holder.name.setTextColor(Color.parseColor(cards.get(position).colors[2]));
        holder.location.setText(cards.get(position).location);
        holder.cardBody.setBackgroundColor(Color.parseColor(cards.get(position).colors[0]));
        holder.cardBottom.setBackgroundColor(Color.parseColor(cards.get(position).colors[1]));
        holder.status.setImageResource(cards.get(position).sprinkler.status == Sprinkler.ONLINE
                ? R.drawable.ic_baseline_online_24
                : R.drawable.ic_baseline_offline_24);
        holder.powerToggle.setOnClickListener(v -> {
            cards.add(position, database.toggleStatus(cards.remove(position)));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void updateCards(ArrayList<Card> cards) {
        this.cards = cards;
        this.notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, location;
        RelativeLayout cardBody;
        LinearLayout cardBottom;
        MaterialCardView card;
        ImageView status;
        ImageButton powerToggle;
        CardClickListener listener;

        public MyViewHolder(@NonNull View itemView, CardClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.cardName);
            location = itemView.findViewById(R.id.cardLocation);
            card = itemView.findViewById(R.id.card);
            cardBody = itemView.findViewById(R.id.cardBody);
            cardBottom = itemView.findViewById(R.id.cardBottom);
            status = itemView.findViewById(R.id.status);
            powerToggle = itemView.findViewById(R.id.powerToggle);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCardClick(getAdapterPosition());
        }
    }

    public interface CardClickListener {
        void onCardClick(int position);
    }
}