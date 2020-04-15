package com.xheghun.vidit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talkgap.R;

public class QuickEffectAdapter extends RecyclerView.Adapter<QuickEffectAdapter.QuickEffectViewHolder> {

    private String title;

    public QuickEffectAdapter(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public QuickEffectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_effect_item,parent,false);
        return new QuickEffectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickEffectViewHolder holder, int position) {
        holder.efectsItemName.setText(title + " "+ position);
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class QuickEffectViewHolder extends RecyclerView.ViewHolder {
        TextView efectsItemName;
        public QuickEffectViewHolder(@NonNull View itemView) {
            super(itemView);
            efectsItemName = itemView.findViewById(R.id.effects_item_name);
        }
    }
}
