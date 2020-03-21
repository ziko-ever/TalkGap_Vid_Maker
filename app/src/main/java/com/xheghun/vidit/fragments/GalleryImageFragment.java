
package com.xheghun.vidit.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.xheghun.vidit.EditActivity;
import com.talkgap.R;
import com.xheghun.vidit.adapter.GalleryMediaAdapter;
import com.xheghun.vidit.adapter.ImageFrameAdapter;
import com.xheghun.vidit.classes.Animate;
import com.xheghun.vidit.classes.FFmpegSingleton;
import com.xheghun.vidit.models.GalleryMedia;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

    /**
     * A simple {@link Fragment} subclass.
     */
    public class GalleryImageFragment extends Fragment {

        static final int REQUEST_PERMISSION_KEY = 1;

        @BindView(R.id.gallery_images_rc)
        RecyclerView recyclerView;

        @BindView(R.id.current_text)
        TextView currentText;

        private GalleryMediaAdapter adapter;

        RelativeLayout selected_photos_layout;

        private Cursor cursor;
        private List<String> selectedImageList;
        private ArrayList<GalleryMedia> imagesList;
        private List<String> imageDate;
        private RecyclerView selectedImages_rc;
        private View root_view;

        //number of current selected item
        private int num;

        public GalleryImageFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            root_view = inflater.inflate(R.layout.fragment_gallery_image, container, false);

            ButterKnife.bind(this, root_view);
            getImages();

            selectedImages_rc = root_view.findViewById(R.id.selected_photos_rc);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            selectedImages_rc.setLayoutManager(layoutManager);

            selectedImageList = new ArrayList<>();

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            setGalleryImagesAdapter();

            return root_view;
        }

    private void getImages() {
        final String[] columns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID,MediaStore.Images.Media.DATE_ADDED};
        final String orderBy = MediaStore.Images.Media.DATE_ADDED;
        //Stores all the images from the gallery in Cursor
        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        //Total number of images
        assert cursor != null;
        int count = cursor.getCount();

        imagesList = new ArrayList<>();
        imageDate = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            int date = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);

            GalleryMedia image = new GalleryMedia();
            image.setPath(cursor.getString(dataColumnIndex));

            imagesList.add(image);
        }
       Collections.reverse(imagesList);
    }
    private void setGalleryImagesAdapter() {
       adapter = new GalleryMediaAdapter(imagesList, getContext(), 1, media -> {
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

            MaterialButton button = root_view.findViewById(R.id.selected_next_btn);
            if (selectedImageList.size() > 3 && selectedImageList.size() < 17) {
                if (button.getVisibility() == View.GONE) {
                    Animate.fadeInAnimation(button,getContext());
                }


                button.setOnClickListener(v -> {

                    String txtPath = Environment.getExternalStorageDirectory()+"/image_path.txt";
                    File txtFile = new File(txtPath);

                    for (int i = 0; i < selectedImageList.size();i++) {
                        String content = "file '"+selectedImageList.get(i)+"' \n";
                        try {
                            FileUtils.writeStringToFile(txtFile,content,"utf-8",true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    File destinationFile = new File(getContext().getFilesDir() +"/video.mp4");
                    if (destinationFile.exists()) {
                        destinationFile.delete();
                    }


                    String[] cmd = {"-y", "-f", "concat", "-r", "1/5", "-safe", "0", "-i", txtFile.getAbsolutePath(), "-preset", "ultrafast", "-vsync", "vfr", "-vf", "scale=1280:720:force_original_aspect_ratio=decrease,pad=1280:720:(ow-iw)/2:(oh-ih)/2,fps=30,format=yuv420p", destinationFile.getAbsolutePath()};
                    Intent intent = new Intent(getContext(), EditActivity.class);
                    intent.putExtra("video_path",destinationFile.getAbsolutePath());
                    FFmpegSingleton.executeCmD(getContext(),cmd,intent);

                });
            } else {
                if (button.getVisibility() == View.VISIBLE) {
                    Animate.fadeOutAnimation(button, getContext());
                }
            }
        });
       recyclerView.setAdapter(adapter);
    }
}
