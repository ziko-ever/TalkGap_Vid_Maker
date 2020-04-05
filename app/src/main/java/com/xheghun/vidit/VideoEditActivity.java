package com.xheghun.vidit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.talkgap.R;
import com.xheghun.vidit.classes.Animate;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoEditActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    VideoView videoView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.cut_tools)
    BottomNavigationView bottom_nav;

    private Intent intent;
    private String videoPath;
    private String newFileName;
    private File dest;
    private int duration;

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
                Toast.makeText(this, "process running", Toast.LENGTH_SHORT).show();
            return true;
        });


        bottom_nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.crop_vid:
                    Toast.makeText(VideoEditActivity.this, "Cropping...", Toast.LENGTH_SHORT).show();
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
