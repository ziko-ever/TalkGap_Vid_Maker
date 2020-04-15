package com.xheghun.vidit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.talkgap.R;
import com.xheghun.vidit.models.ClipEditItemModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClipEditRecyclerAdapter extends RecyclerView.Adapter<ClipEditRecyclerAdapter.ClipEditViewHolder> {
    private Context context;
    private ArrayList<ClipEditItemModel> items;

    public ClipEditRecyclerAdapter(Context context, ArrayList<ClipEditItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ClipEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clip_edit_recycler_item,parent,false);
        return new ClipEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipEditViewHolder holder, int position) {
        ClipEditItemModel itemModel = items.get(position);
        Glide.with(context).load(itemModel.getDrawable()).into(holder.imageView);
        holder.textView.setText(itemModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ClipEditViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ClipEditViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_title);
        }
    }
}
