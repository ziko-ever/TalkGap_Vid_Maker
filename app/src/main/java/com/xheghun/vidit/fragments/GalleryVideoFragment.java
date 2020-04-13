package com.xheghun.vidit.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.talkgap.R;
import com.xheghun.vidit.VideoEditActivity;
import com.xheghun.vidit.adapter.GalleryImageAdapter;
import com.xheghun.vidit.adapter.GalleryVideoAdapter;
import com.xheghun.vidit.adapter.ImageFrameAdapter;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryVideoFragment extends Fragment {

    private Cursor cursor;
    private List<GalleryMedia> videoList;

    private RecyclerView selectedImages_rc;
    private List<String> selectedImageList;

    @BindView(R.id.video_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.current_text)
    TextView currentText;

    private int num;
    public GalleryVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_video, container, false);
        ButterKnife.bind(this,view);
        getVideos();

        selectedImages_rc = view.findViewById(R.id.selected_photos_rc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        selectedImages_rc.setLayoutManager(layoutManager);

        selectedImageList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(new GalleryVideoAdapter(videoList, getContext() , media -> {
            //check if image is already selected
            //check if image is already selected
            if (!selectedImageList.contains(media.getPath())) {
                if (selectedImageList.size() >= 16) {
                    Toast.makeText(getContext(),"Maximum number of photos exceeded",Toast.LENGTH_SHORT).show();
                }else {
                    selectedImageList.add(media.getPath());
                    num = selectedImageList.size();
                }

            }else if (selectedImageList.contains(media.getPath())) {
                selectedImageList.remove(media.getPath());
            }

            if (selectedImageList.size() > 16) {
                Toast.makeText(getContext(),"Maximum number of photos exceeded",Toast.LENGTH_SHORT).show();
            }

            currentText.setText(getResources().getString(R.string.current_photo)+" "+selectedImageList.size());

            //populate recyclerview
            selectedImages_rc.setAdapter(new ImageFrameAdapter(selectedImageList, getContext(), (path, position) -> {
                selectedImageList.remove(path);
                recyclerView.getAdapter().notifyDataSetChanged();
                selectedImages_rc.getAdapter().notifyDataSetChanged();

            }));
            selectedImages_rc.scrollToPosition(selectedImages_rc.getChildCount());

            // Collections.reverse(selectedImageList);

            MaterialButton button = view.findViewById(R.id.selected_next_btn);
            if (selectedImageList.size() > 3 && selectedImageList.size() < 17) {
                // if (button.getVisibility() == View.GONE) {
                button.setEnabled(true);
                //}


                button.setOnClickListener(v -> startActivity(new Intent(getContext(), VideoEditActivity.class)));
            } else {
                //if (button.getVisibility() == View.VISIBLE) {
                button.setEnabled(false);
                //Animate.fadeOutAnimation(button, getContext());
                //}
            }
        }));

        return view;
    }

    private void getVideos() {
        final String[] columns = {MediaStore.Video.VideoColumns.DATA,MediaStore.Video.Media._ID};
        final String orderBy = MediaStore.Video.Media.DATE_ADDED;
        //Stores all the images from the gallery in Cursor
        cursor = getActivity().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        //Total number of images
        assert cursor != null;
        int count = cursor.getCount();

        videoList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);

            GalleryMedia video = new GalleryMedia();
            video.setPath(cursor.getString(dataColumnIndex));
            videoList.add(video);
        }

        Collections.reverse(videoList);
    }


}
