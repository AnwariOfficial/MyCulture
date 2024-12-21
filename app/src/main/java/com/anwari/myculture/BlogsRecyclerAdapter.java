package com.anwari.myculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.util.List;


public class BlogsRecyclerAdapter extends RecyclerView.Adapter<BlogsRecyclerAdapter.ViewHolder> {

    private List<Blogs> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    BlogsRecyclerAdapter(Context context, List<Blogs> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.english_blogs_recycler_view, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blogs news = mData.get(position);
        holder.myTextView.setText(news.getNews_title());
        holder.newsImage.setImageResource(news.getNews_image());
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(news.getNews_content())
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String languageCode) {
                                if (languageCode.equals("ps") ||languageCode.equals("fa")) {
                                    holder.myTextView.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    holder.myTextView.setTextDirection(View.LAYOUT_DIRECTION_RTL);
                                    holder.contentImage.setTextDirection(View.LAYOUT_DIRECTION_RTL);
                                    holder.contentImage.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    holder.contentImage.setPadding(0,0,8,0);
                                    holder.myTextView.setPadding(8,0,8,0);
                                } else if(languageCode.equals("en")) {
                                    holder.myTextView.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    holder.myTextView.setTextDirection(View.LAYOUT_DIRECTION_LTR);
                                    holder.contentImage.setTextDirection(View.LAYOUT_DIRECTION_LTR);
                                    holder.contentImage.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    holder.contentImage.setPadding(0,0,8,0);
                                    holder.myTextView.setPadding(8,0,8,0);
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                System.out.println("Failure Listiner");
                            }
                        });

        holder.contentImage.setText(news.getNews_content());
        holder.b_author.setText(news.getAuthor());



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView newsImage;
        TextView contentImage;
        TextView b_author;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.news_title);
            newsImage = itemView.findViewById(R.id.news_image);
            contentImage = itemView.findViewById(R.id.news_content);
            b_author = itemView.findViewById(R.id.blogs_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Blogs getItem(int id) {
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
