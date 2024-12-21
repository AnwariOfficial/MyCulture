package com.anwari.myculture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FullPhotoActivity extends AppCompatActivity {
    RecyclerView commentsRecycler;
    List<Comment> list;
    CommentRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);

        commentsRecycler = findViewById(R.id.recyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(FullPhotoActivity.this,RecyclerView.VERTICAL,false);
        commentsRecycler.setLayoutManager(layoutManager);
        commentsRecycler.setItemAnimator( new DefaultItemAnimator());

        list = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setImage(R.drawable.profile);
        comment1.setUsername("Salim Anwari");
        comment1.setCommentText("Awesome Photo");

        Comment comment2 = new Comment();
        comment2.setImage(R.drawable.anwari);
        comment2.setUsername("Najibullah Anwari");
        comment2.setCommentText("Nice Photo bro..");

        Comment comment3 = new Comment();
        comment3.setImage(R.drawable.news_image);
        comment3.setUsername("Ali Ahmadi");
        comment3.setCommentText("you look Awesome, happy to see you buddy.");
        list.add(comment1);
        list.add(comment2);
        list.add(comment3);

        adapter = new CommentRecyclerAdapter(this,list,"english");
        commentsRecycler.setAdapter(adapter);

    }
}