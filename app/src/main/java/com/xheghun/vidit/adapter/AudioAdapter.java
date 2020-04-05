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
    private MediaPlayer mp;
    private int duration;
    private int amountToUpdate;
    private int start;
    private int end;

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
        try {
            holder.wave.setRawData(AudioManager.getInstance(mContext).convert(audio.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            holder.wave.setRawData(audio.getRaw());
        }

        holder.bind(audio);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = audio.isExpanded();
            audio.setExpanded(!expanded);
            notifyItemChanged(i);
            Log.d("Audio path", audio.getPath());
            Toast.makeText(mContext, "Item clicked = " + i, Toast.LENGTH_LONG).show();

            if(audio.isExpanded())
            {
                try {
                    holder.play(audio.getPath(),0, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                //listener.onStop();
                holder.stop();
            }
        });

        holder.wave.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onStartTracking(float v) {
                Toast.makeText(mContext,v + " start position" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTracking(float v) {
                Toast.makeText(mContext,v + " end position" , Toast.LENGTH_SHORT).show();
                if (!(amountToUpdate * v >= duration)) {
                    int p = (int)v;

                    p += 1;
                    mp.seekTo(amountToUpdate * (int)v);

                    start = amountToUpdate * (int)v;

                    holder.wave.setProgress(p);
                }
            }

            @Override
            public void onProgressChanged(float v, boolean b) {

            }
        });

        holder.useAudio.setOnClickListener(v -> {
            audio.setStart(start);
            audio.setEnd(end);

            listener.onAudioSelected(audio);
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{



        final TextView artist, displayName, duration;
        private ImageView placeholder;
        private RelativeLayout parentLayout;
        private LinearLayout subItem;
        private AudioWaveView wave;
        private Button useAudio;



        ViewHolder(View view) {
            super(view);

            artist =  view.findViewById(R.id.artist);
            displayName = view.findViewById(R.id.displayname);
            duration = view.findViewById(R.id.duration);
            placeholder = view.findViewById(R.id.thumbnail);
            parentLayout = view.findViewById(R.id.parent_layout);
            subItem = view.findViewById(R.id.sub_item);
            wave = view.findViewById(R.id.audio_wave);
            useAudio = view.findViewById(R.id.useAudio);





        }


        private void play(String filename, int start, int end) throws IOException
        {
            mp = new MediaPlayer();


                mp.setDataSource(filename);
                mp.prepare();
                mp.start();

            AudioAdapter.this.duration = mp.getDuration();
            amountToUpdate = AudioAdapter.this.duration / 100;


            if (timer != null) {
                timer.shutdown();
                timer = null;
            }
            timer = new ScheduledThreadPoolExecutor(1);
            // This process will execute immediately, then execute every 3 seconds.
            timer.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Do something worthwhile

                            if (!(amountToUpdate * wave.getProgress() >= AudioAdapter.this.duration)) {
                                int p = (int)wave.getProgress();

                                p += 1;
                                wave.setProgress(p);
                            }
                            //Toast.makeText(mContext,mp.getDuration() + "%" , Toast.LENGTH_SHORT).show();
                            //wave.setProgress(mp.getCurrentPosition());

                        }
                    });
                }
            }, 0L, amountToUpdate, TimeUnit.MILLISECONDS);
        }

        public void stop()
        {
            //set up MediaPlayer
            if(mp != null)
            {


                    mp.stop();
                    mp.release();

            }


        }


        // Method in ViewHolder class
        private void bind(Audio audio) {
            // Get the state
            boolean expanded = audio.isExpanded();
            // Set the visibility based on state
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);



//            title.setText(movie.getTitle());
//            genre.setText("Genre: " + movie.getGenre());
//            year.setText("Year: " + movie.getYear());
        }
    }


    public void filter(String queryText)
    {
        mItems.clear();


        if(queryText.equalsIgnoreCase(""))
        {
            mItems.addAll(list);
            //Toast.makeText(mContext, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(Audio teaching: list)
            {
                if(teaching.getDisplayname().toLowerCase().contains(queryText.toLowerCase()))
                {
                    mItems.add(teaching);
                }
            }

        }

        notifyDataSetChanged();
    }



}
