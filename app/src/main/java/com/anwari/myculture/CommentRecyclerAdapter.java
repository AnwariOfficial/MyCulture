package com.anwari.myculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.List;


public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    private List<Comment> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String language;
    // data is passed into the constructor
    CommentRecyclerAdapter(Context context, List<Comment> data, String language) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.language = language;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if(language.equals("dari")){
         view = mInflater.inflate(R.layout.comment_layout, parent, false);
    }
    else if(language.equals("pashto")){
         view = mInflater.inflate(R.layout.comment_layout, parent, false);
    }
    else{
         view = mInflater.inflate(R.layout.comment_layout, parent, false);
    }

        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment idea = mData.get(position);
       holder.image.setImageResource(idea.getImage());
       holder.username.setText(idea.getUsername());
       holder.comment.setText(idea.getCommentText());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView username;
        TextView comment;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.displayComment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Comment getItem(int id) {
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
