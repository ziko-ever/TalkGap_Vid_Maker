package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talkgap.R;
import com.xheghun.vidit._interface.EffectsItemClickListener;
import com.xheghun.vidit.models.EffectsItem;

import java.util.ArrayList;
import java.util.List;

public class CamEffectsAdapter extends RecyclerView.Adapter<CamEffectsAdapter.CamEffectsViewHolder> {


    private Context context;
    private ArrayList<EffectsItem> effects;
    private EffectsItemClickListener listener;

    public CamEffectsAdapter(Context context, ArrayList<EffectsItem> effects, EffectsItemClickListener listener) {
        this.context = context;
        this.effects = effects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CamEffectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cam_effect_item,parent,false);
        return new CamEffectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamEffectsViewHolder holder, int position) {
      holder.bind(effects.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return effects.size();
    }

    class CamEffectsViewHolder extends RecyclerView.ViewHolder {
        ViewGroup lockedView;
        CamEffectsViewHolder(@NonNull View itemView) {
            super(itemView);
            lockedView = itemView.findViewById(R.id.locked_layout);
        }

        void bind(EffectsItem effectsItem, EffectsItemClickListener listener) {
            if (effectsItem.isLocked()) {
                lockedView.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(v -> listener.onClick(effectsItem));
        }
    }
}
