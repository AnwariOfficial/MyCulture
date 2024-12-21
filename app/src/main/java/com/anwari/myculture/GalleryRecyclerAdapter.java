package com.anwari.myculture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
//sdfsdhkfkshfs
import androidx.recyclerview.widget.RecyclerView;

//import com.like.LikeButton;
//import com.like.OnLikeListener;

import java.util.List;


public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {

    private List<Integer> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String language;
    // data is passed into the constructor
    GalleryRecyclerAdapter(Context context, List<Integer> data, String language) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.language = language;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if(language.equals("dari")){
         view = mInflater.inflate(R.layout.dari_gallery_photo, parent, false);
    }
    else if(language.equals("pashto")){
         view = mInflater.inflate(R.layout.pashto_gallery_photo, parent, false);
    }
    else{
         view = mInflater.inflate(R.layout.english_gallery_photo, parent, false);
    }

        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int idea = mData.get(position);
       holder.photo.setImageResource(idea);
//        holder.imageButtonLike.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//
//            }
//        });
        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FullPhotoActivity.class);
                int image = getItem(position);
                intent.putExtra("image",image);
                v.getContext().startActivity(intent);
            }
        });
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FullPhotoActivity.class);
                int image = getItem(position);
                intent.putExtra("image",image);
                v.getContext().startActivity(intent);
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView photo;
     //   LikeButton imageButtonLike;
        ImageButton commentButton;
        ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            //imageButtonLike = itemView.findViewById(R.id.like_button);
            commentButton = itemView.findViewById(R.id.comment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Integer getItem(int id) {
        return mData.get(id);
    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
