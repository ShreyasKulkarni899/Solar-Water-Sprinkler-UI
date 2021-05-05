package com.nkocet.untitled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    List<ListModel> objectList;
    LayoutInflater inflater;
    Context context;

    public ListAdapter(Context context, List<ListModel> objectList) {
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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked " + holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.setData(current, position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imgThumb;
        int position;
        ListModel currentObject;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.list_item_parent);
            title = itemView.findViewById(R.id.tvTitle);
            imgThumb = itemView.findViewById(R.id.img_tmb);
        }

        public void setData(ListModel current, int position) {
            this.title.setText(current.getTitle());
            this.imgThumb.setImageResource(current.getImageId());
            this.position = position;
            this.currentObject = current;
        }
    }
}
