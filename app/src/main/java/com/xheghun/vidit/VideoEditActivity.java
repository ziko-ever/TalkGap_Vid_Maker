package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.talkgap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoEditActivity extends AppCompatActivity {

    @BindView(R.id.image_thumb)
    ImageView image;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.cut_tools)
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.done:
                    startActivity(new Intent(this,SelfieVideoEditActivity.class));
                    break;
                case R.id.undo:
                    Toast.makeText(this, "undo", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.redo:
                    Toast.makeText(this, "redo", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });


        bottom_nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_music:
                    Toast.makeText(VideoEditActivity.this, "Cropping...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, AddMusicActivity.class));
                    break;
                case R.id.cut_vid:
                    Toast.makeText(VideoEditActivity.this, "Cutting...", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rotate_vid:
                    Toast.makeText(VideoEditActivity.this, "Rotating...", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

    }

    private void executeCommand(String[] cmd) {
    }
}
