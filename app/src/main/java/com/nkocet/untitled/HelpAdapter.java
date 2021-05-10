package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    ArrayList<HelpModel> objectList;
    LayoutInflater inflater;
    Context context;

    public HelpAdapter(Context context, ArrayList<HelpModel> objectList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HelpAdapter.MyViewHolder(inflater.inflate(R.layout.card_help, parent, false));
    }

    @Override
    public int getItemCount()
    {
        return objectList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HelpModel current = objectList.get(position);
        holder.setData(current, position);
        holder.cardView.setOnClickListener(v -> context.startActivity(new Intent(context, HelpDetailsPane.class).putExtra("object", holder.currentObject)));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextHelp;
        int position;
        HelpModel currentObject;
        MaterialCardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.list_item_parent_help);
            headerTextHelp = itemView.findViewById(R.id.headerTextHelp);

        }

        public void setData(HelpModel current, int position) {
            this.headerTextHelp.setText(current.headerTextHelp);
            this.position = position;
            this.currentObject = current;
        }
    }
}
