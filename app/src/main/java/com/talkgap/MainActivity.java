package com.talkgap;

import android.Manifest;
import android.app.Dialog;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.xheghun.vidit.adapter.TabAdapter;
import com.xheghun.vidit.fragments.FourSnapFragment;
import com.xheghun.vidit.fragments.GalleryFragment;
import com.xheghun.vidit.fragments.SelfieFragment;


public class MainActivity extends FragmentActivity {

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.LOCATION_HARDWARE
    };

    private final int PERMISSION_REQUEST_CODE = 3498;
    private TabAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //ViewGroup mainContainer = findViewById(R.id.main_container);

        //mainContainer.setPadding(0,0,0,getNavBarHeight());
        //final



//            if (mainContainer.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) mainContainer.getLayoutParams();
//                p.setMargins(0, 0, 0, getNavBarHeight());
//                mainContainer.requestLayout();
//            }




        if (hasNoPermission()) {
            requestPermission();
        }
    }



    public int getNavBarHeight() {
        int result = 0;
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            Resources resources = this.getResources();

            int orientation = resources.getConfiguration().orientation;
            int resourceId;
            if (isTablet()){
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            }  else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }


    private boolean isTablet() {
        return (this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(),4);
        adapter.addFragment(new GalleryFragment(), getResources().getString(R.string.gallery));
        adapter.addFragment(new SelfieFragment(),"Selfie");
        adapter.addFragment(new FourSnapFragment(), getResources().getString(R.string.four_snaps));
        adapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void requestPermission() {
        // !! means cannot be null
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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