package com.nkocet.untitled;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<ListModel> objectList;
    private LayoutInflater inflater;

    public ListAdapter(Context context,List<ListModel> objectList ){
        inflater = LayoutInflater.from(context);
        this.objectList = objectList ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount()
    {
        return objectList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListModel current = objectList.get(position);
        holder.setData(current,position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title ;
        private ImageView imgthumb;
        private int position;
        private  ListModel currentObject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            imgthumb = (ImageView) itemView.findViewById(R.id.img_tmb);
        }

        public void setData(ListModel current, int position) {
            this.title.setText(current.getTitle());
            this.imgthumb.setImageResource(current.getImageId());
            this.position = position ;
            this.currentObject = current;
        }
    }
}
