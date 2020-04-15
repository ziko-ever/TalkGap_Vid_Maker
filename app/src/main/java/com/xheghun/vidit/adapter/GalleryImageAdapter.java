package com.xheghun.vidit.adapter;

import android.content.Context;
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
import com.xheghun.vidit.models.GalleryMedia;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder> {

    private List<GalleryMedia> images;
    private Context mContext;
    private final GalleryItemClickListener itemClickListener;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public GalleryImageAdapter(List<GalleryMedia> images, Context mContext, GalleryItemClickListener itemClickListener) {
        this.images = images;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_image_item,parent,false);
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageViewHolder holder, int position) {
        holder.bind(images.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mediaSelected;
        GalleryImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaSelected = itemView.findViewById(R.id.item_selected);
            imageView = itemView.findViewById(R.id.media_item);
        }

        void bind(final GalleryMedia media, final GalleryItemClickListener listener) {
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

     public interface GalleryItemClickListener {
        void onItemClick(GalleryMedia media);
    }
}
