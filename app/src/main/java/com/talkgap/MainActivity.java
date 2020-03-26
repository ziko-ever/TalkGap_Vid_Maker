package com.talkgap;

import android.Manifest;
import android.app.Dialog;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.xheghun.vidit.adapter.TabAdapter;
import com.xheghun.vidit.fragments.FourSnapFragment;
import com.xheghun.vidit.fragments.GalleryFragment;
import com.xheghun.vidit.fragments.SelfieFragment;


public class MainActivity extends AppCompatActivity{

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.LOCATION_HARDWARE
    };

    private final int PERMISSION_REQUEST_CODE = 3498;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (hasNoPermission()) {
            requestPermission();
        }
    }


    private void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("talkgap")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    //Home.this.finish();
                })
                .create()
                .show();

    }

    private void loadFragments() {
        viewPager = findViewById( R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new GalleryFragment(), getResources().getString(R.string.gallery));
        adapter.addFragment(new SelfieFragment(),"Selfie");
        //adapter.addFragment(new FourSnapFragment(), getResources().getString(R.string.four_snaps));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void requestPermission() {
        // !! means cannot be null
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadFragments();
            }
        }
    }

    private boolean hasNoPermission() {
        return  ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED;
    }
}