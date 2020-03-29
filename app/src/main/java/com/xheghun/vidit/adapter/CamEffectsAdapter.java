package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talkgap.R;

public class CamEffectsAdapter extends RecyclerView.Adapter<CamEffectsAdapter.CamEffectsViewHolder> {


    private Context context;
    private int count;

    public CamEffectsAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    @NonNull
    @Override
    public CamEffectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cam_effect_item,parent,false);
        return new CamEffectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamEffectsViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class CamEffectsViewHolder extends RecyclerView.ViewHolder {
        CamEffectsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
