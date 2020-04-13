package com.xheghun.vidit.adapter;

import android.app.Activity;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.talkgap.R;
import com.xheghun.vidit._interface.AudioClickListener;
import com.xheghun.vidit.classes.AudioManager;
import com.xheghun.vidit.models.Audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private List<Audio> mItems, list;
    private ScheduledThreadPoolExecutor timer;
    private Audio item;
    private Activity mContext;
    private AudioClickListener listener;


    public AudioAdapter(Activity mContext, List<Audio> items, AudioClickListener listener)
    {
        this.mContext = mContext;
        this.mItems = items;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        this.listener = listener;
    }

    @NotNull
    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_item, viewGroup, false);

        return new AudioAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AudioAdapter.ViewHolder holder, final int i) {
        final Audio audio = mItems.get(i);

       // audio = item;

        holder.artist.setText(audio.getArtist());
        holder.displayName.setText(audio.getDisplayname());
        holder.duration.setText(audio.getDuration());
        Glide.with(mContext).load(R.drawable.afro_woman).into(holder.placeholder);

        holder.wave.setScaledData(new byte[0]);
        holder.wave.setProgress(0);

        holder.bind(audio);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = audio.isExpanded();
            audio.setExpanded(!expanded);
            notifyItemChanged(i);
            Log.d("Audio path", audio.getPath());
            Toast.makeText(mContext, "Item clicked = " + i, Toast.LENGTH_LONG).show();

            if(audio.isExpanded()) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{



        final TextView artist, displayName, duration;
        private ImageView placeholder;
        private LinearLayout subItem;
        private AudioWaveView wave;
        private Button useAudio;



        ViewHolder(View view) {
            super(view);

            artist =  view.findViewById(R.id.artist);
            displayName = view.findViewById(R.id.displayname);
            duration = view.findViewById(R.id.duration);
            placeholder = view.findViewById(R.id.thumbnail);
            subItem = view.findViewById(R.id.sub_item);
            wave = view.findViewById(R.id.audio_wave);
            useAudio = view.findViewById(R.id.useAudio);





        }


        // Method in ViewHolder class
        private void bind(Audio audio) {
            // Get the state
            boolean expanded = audio.isExpanded();
            // Set the visibility based on state
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        }
    }




}
