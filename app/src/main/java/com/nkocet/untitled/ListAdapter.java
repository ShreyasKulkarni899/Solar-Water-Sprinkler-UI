package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    ArrayList<ListModel> objectList;
    LayoutInflater inflater;
    Context context;

    public ListAdapter(Context context, ArrayList<ListModel> objectList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return objectList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListModel current = objectList.get(position);
        holder.setData(current, position);
        holder.cardView.setOnClickListener(v -> context.startActivity(new Intent(context, CropDetailsPane.class)
                .putExtra("object", holder.currentObject)));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imgThumb;
        int position;
        ListModel currentObject;
        MaterialCardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.list_item_parent);
            title = itemView.findViewById(R.id.tvTitle);
            imgThumb = itemView.findViewById(R.id.img_tmb);
        }

        public void setData(ListModel current, int position) {
            this.title.setText(current.title);
            this.imgThumb.setImageResource(current.imageId);
            this.position = position;
            this.currentObject = current;
        }
    }
}
