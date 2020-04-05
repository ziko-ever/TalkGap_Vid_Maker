package com.xheghun.vidit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.talkgap.R;
import com.xheghun.vidit._interface.AudioClickListener;
import com.xheghun.vidit.adapter.AudioAdapter;
import com.xheghun.vidit.classes.AudioManager;
import com.xheghun.vidit.models.Audio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class AddMusicActivity extends AppCompatActivity implements AudioClickListener {

    private RecyclerView recyclerView;
    private List<Audio> audioList;
    private LinearLayoutManager layoutManager;
    private AudioAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        recyclerView =  findViewById(R.id.my_recycler_view);

        audioList = AudioManager.getInstance(this).listAudio();
        ImageButton back_btn = findViewById(R.id.toolbar_back_btn);

        back_btn.setOnClickListener(v -> finish());

        //Toast.makeText(this,String.valueOf(audioList.size()), Toast.LENGTH_LONG).show();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        // specify an adapter (see also next example)
        mAdapter = new AudioAdapter(this,audioList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClicked() {

    }

    @Override
    public void onPlay(String filename, int start, int end) {
        Log.d("audio", filename);
        AudioManager.getInstance(this).play(filename, start, end);
    }

    @Override
    public void onStop() {
        super.onStop();
        AudioManager.getInstance(this).stop();
    }

    @Override
    public void onAudioSelected(Audio audio) {
        Intent i = new Intent("audiokey");
        i.putExtra("aud", audio);
        setResult(Activity.RESULT_OK, i);
        finish();
    }


}
