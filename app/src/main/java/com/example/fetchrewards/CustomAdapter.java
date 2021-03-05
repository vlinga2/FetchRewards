package com.example.fetchrewards;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<ListItem>  listItems;
    private LayoutInflater mInflater;
    private ListItemClickListener mClickListener;
    private int[] colors = new int[]{Color.rgb(0,160,160),
            Color.rgb(40,80,160),
            Color.rgb(160,0,160),
            Color.rgb(30,80,160)};

    public CustomAdapter(@NonNull Context context, ArrayList<ListItem> listItems) {
        this.mInflater = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView name;
        TextView listId;
        TextView id;
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            listId = itemView.findViewById(R.id.listID);
            id = itemView.findViewById(R.id.ID);
            view = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null) mClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        TextView name = holder.name;
        TextView listId = holder.listId;
        TextView id = holder.id;

        name.setText(listItems.get(position).getName());
        listId.setText(listItems.get(position).getListId());
        id.setText(listItems.get(position).getId());
        holder.view.setBackgroundColor(colors[Integer.parseInt(listItems.get(position).getListId())-1]);
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    void setClickListener(ListItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    interface ListItemClickListener{
        void onItemClick(View view, int position);
    }
}
