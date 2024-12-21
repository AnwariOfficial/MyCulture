package com.anwari.myculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class IdeaRecyclerAdapter extends RecyclerView.Adapter<IdeaRecyclerAdapter.ViewHolder> {

    private List<Idea> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    IdeaRecyclerAdapter(Context context, List<Idea> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.english_recent_idea_post, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         Idea idea = mData.get(position);
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(idea.getIdeaPost())
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String languageCode) {
                                if (languageCode.equals("ps") || languageCode.equals("fa")) {
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    holder.ideaPost.setPadding(0,0,8,0);
                                } else if(languageCode.equals("en")) {
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    holder.ideaPost.setTextDirection(View.TEXT_DIRECTION_LTR);
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
        Picasso.get().load(idea.getProfilePhoto()).into(holder.profilePhoto);
       //holder.profilePhoto.setImageResource(R.drawable.anwari);
       holder.userName.setText(idea.getUserName());
       holder.date.setText(String.valueOf(idea.getPostDate()));
       holder.ideaPost.setText(idea.getIdeaPost());
       if(idea.getAudio()!=null){

       }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profilePhoto;
        TextView userName;
        TextView date;
        TextView ideaPost;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profilePhoto);
            userName = itemView.findViewById(R.id.recentIdeaUsername);
            date = itemView.findViewById(R.id.recentIdeaDate);
            ideaPost = itemView.findViewById(R.id.recentIdeaPostContainer);
            linearLayout = itemView.findViewById(R.id.posted_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Idea getItem(int id) {
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
