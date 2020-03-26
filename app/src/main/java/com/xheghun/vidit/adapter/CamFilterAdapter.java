package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talkgap.R;

public class CamFilterAdapter extends RecyclerView.Adapter<CamFilterAdapter.CamViewHolder> {

    private Context context;
    private String title;

    public CamFilterAdapter(Context context, String title) {
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public CamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_item,parent,false);
        return new CamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamViewHolder holder, int position) {
        holder.textView.setText(title+position);
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class CamViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CamViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.filter_name);
        }
    }
}
