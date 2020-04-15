package com.xheghun.vidit.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.talkgap.R;
import com.xheghun.vidit.TrimActivity;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.List;

public class GalleryVideoAdapter extends RecyclerView.Adapter<GalleryVideoAdapter.GalleryVideoViewHolder> {
    private List<GalleryMedia> images;
    private Context mContext;

    private final GalleryVideoItemClickListener itemClickListener;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public GalleryVideoAdapter(List<GalleryMedia> images, Context mContext, GalleryVideoItemClickListener itemClickListener) {
        this.images = images;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GalleryVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_gallery_item,parent,false);

        return new GalleryVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryVideoViewHolder holder, int position) {
        holder.bind(images.get(position), itemClickListener);
        holder.cutVid.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, TrimActivity.class)));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class GalleryVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView mediaSelected;
        ImageView cutVid;

        GalleryVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaSelected = itemView.findViewById(R.id.item_selected);
            imageView = itemView.findViewById(R.id.media_item);
            cutVid = itemView.findViewById(R.id.cut_vid_btn);
        }

        void bind(final GalleryMedia media, final GalleryVideoItemClickListener listener) {
            // Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
            Glide.with(mContext).load(media.getPath())
                    .override(imageView.getWidth(),imageView.getHeight())
                    .into(imageView);
            itemView.setOnClickListener(v -> {
                int adapaterPosition = getAdapterPosition();
                if (!itemStateArray.get(adapaterPosition, false) && mediaSelected != null ) {
                    if (itemStateArray.size() <= 16) {
                        mediaSelected.setVisibility(View.VISIBLE);
                        mediaSelected.setText(String.valueOf(itemStateArray.size()+1));
                        itemStateArray.put(adapaterPosition, true);
                    }
                } else {
                    if (mediaSelected != null) {
                        mediaSelected.setText("");
                        mediaSelected.setVisibility(View.GONE);
                    }
                    itemStateArray.put(adapaterPosition,false);
                }
                listener.onItemClick(media);
            });
        }
    }

    public interface GalleryVideoItemClickListener {
        void onItemClick(GalleryMedia media);
    }
}
