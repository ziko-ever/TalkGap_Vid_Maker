package com.xheghun.vidit.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.talkgap.R;
import com.xheghun.vidit.adapter.TabAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {


    private static final int REQUEST_PERMISSION_KEY = 100;
    @BindView(R.id.gallery_viewpager)
    ViewPager pager;
    @BindView(R.id.gallery_tab_layout)
    TabLayout tabLayout;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_gallery,container,false);
        ButterKnife.bind(this,view);
        checkForPermission();
        return view;
    }

    private void checkForPermission() {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager(), 0);
                            adapter.addFragment(new GalleryImageFragment(), getResources().getString(R.string.images_tab));
                            adapter.addFragment(new GalleryVideoFragment(), getResources().getString(R.string.videos_tab));
                            pager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(pager);
                        } else {
                            Toast.makeText(getContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}