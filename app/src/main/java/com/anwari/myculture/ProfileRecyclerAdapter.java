package com.anwari.myculture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder> {

    private List<Idea> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    ProfileRecyclerAdapter(Context context, List<Idea> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.english_my_recent_idea_post, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
       holder.profilePhoto.setImageResource(R.drawable.anwari);
       holder.userName.setText(idea.getUserName());
       holder.date.setText(String.valueOf(idea.getPostDate()));
       holder.ideaPost.setText(idea.getIdeaPost());
       holder.menuOptions.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               PopupMenu popup = new PopupMenu(context, holder.menuOptions);
               //inflating menu from xml resource
               popup.inflate(R.menu.post_menu);

               //adding click listener
               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                           if (item.getItemId() == R.id.edit) {
                               String post = holder.ideaPost.getText().toString();
                               Intent intent = new Intent(context, IdeasActivity.class);
                               intent.putExtra("post", post);
                               intent.putExtra("flag", true);
                               context.startActivity(intent);
                           }
                           else if(item.getItemId() == R.id.delete) {
                               mData.remove(position);
                           }

                       return false;
                   }
               });
               //displaying the popup
               popup.show();

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
        CircleImageView profilePhoto;
        TextView userName;
        TextView date;
        TextView ideaPost;
        TextView menuOptions;

        ViewHolder(View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profilePhoto);
            userName = itemView.findViewById(R.id.recentIdeaUsername);
            date = itemView.findViewById(R.id.recentIdeaDate);
            ideaPost = itemView.findViewById(R.id.recentIdeaPostContainer);
            menuOptions= itemView.findViewById(R.id.textViewOptions);
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
