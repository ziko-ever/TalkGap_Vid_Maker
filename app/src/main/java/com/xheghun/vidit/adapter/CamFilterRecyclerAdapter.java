package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talkgap.R;

public class CamFilterRecyclerAdapter extends RecyclerView.Adapter<CamFilterRecyclerAdapter.CamFilterViewHolder> {

    private String title;
    private Context context;

    public CamFilterRecyclerAdapter(String title, Context context) {
        this.title = title;
        this.context = context;
    }

    @NonNull
    @Override
    public CamFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recycler_item,parent,false);
        return new CamFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamFilterViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> Toast.makeText(context,title+" "+position+" clicked",Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class CamFilterViewHolder extends RecyclerView.ViewHolder {

        public CamFilterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
