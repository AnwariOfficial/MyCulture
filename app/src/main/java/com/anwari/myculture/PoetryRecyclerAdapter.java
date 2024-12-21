package com.anwari.myculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.util.List;


public class PoetryRecyclerAdapter extends RecyclerView.Adapter<PoetryRecyclerAdapter.ViewHolder> {

    private List<Poetry> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    PoetryRecyclerAdapter(Context context, List<Poetry> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.english_poetry_recycler_view, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Poetry idea = mData.get(position);
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(idea.getIdeaPost())
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String languageCode) {
                                if (languageCode.equals("ps") || languageCode.equals("fa")) {
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    holder.ideaPost.setTextDirection(View.LAYOUT_DIRECTION_RTL);
                                    holder.ideaPost.setPadding(0,0,8,0);
                                } else if(languageCode.equals("en")) {
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    holder.ideaPost.setTextDirection(View.LAYOUT_DIRECTION_LTR);
                                    holder.ideaPost.setPadding(8,0,0,0);

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
       holder.userName.setText(idea.getUserName());
       holder.date.setText(String.valueOf(idea.getPostDate()));
       holder.ideaPost.setText(idea.getIdeaPost());
      // holder.author.setText(idea.getAuthor());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName;
        TextView date;
        TextView ideaPost;
        //TextView author;

        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.date);
            ideaPost = itemView.findViewById(R.id.poem);
           // author = itemView.findViewById(R.id.author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Poetry getItem(int id) {
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
