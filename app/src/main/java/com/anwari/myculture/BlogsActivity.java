package com.anwari.myculture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class BlogsActivity extends AppCompatActivity implements BlogsRecyclerAdapter.ItemClickListener{
    SharedPreferences sharedPref;
    BlogsRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_news);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_navigation_blogs);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_navigation_blogs);
        }
        else{
            setContentView(R.layout.english_navigation_blogs);
        }
        //setContentView(R.layout.english_navigation_layout);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.navigation_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.stopen, R.string.stclose);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    Intent intent = new Intent(BlogsActivity.this, BlogsActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(BlogsActivity.this,IdeasActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(BlogsActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(BlogsActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.survey){
                    Intent intent = new Intent(BlogsActivity.this,SurveysActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(BlogsActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });




        // data to populate the RecyclerView with
        ArrayList<Blogs> animalNames = new ArrayList<>();
        Blogs n1 = new Blogs();
        n1.setNews_content(getResources().getString(R.string.p_news_content));
        n1.setNews_image(R.drawable.image7);
        n1.setNews_title(getResources().getString(R.string.heading4));
        n1.setAuthor("Ahmadullah Ahmadi");

        Blogs n2 = new Blogs();
        n2.setNews_content(getResources().getString(R.string.news1));
        n2.setNews_image(R.drawable.image14);
        n2.setNews_title("France is developed country");
        n2.setAuthor("Ahmadullah Ahmadi");
        Blogs n3 = new Blogs();
        n3.setNews_content(getResources().getString(R.string.news3));
        n3.setNews_image(R.drawable.image19);
        n3.setNews_title(getResources().getString(R.string.heading3));
        n3.setAuthor("Ahmadullah Ahmadi");
        Blogs n4 = new Blogs();
        n4.setNews_content("Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add.\n" +
                "        You can also type a keyword to search online for the video that best fits your document. Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add. You can also type a keyword to search online for the video that best fits your document.");
        n4.setNews_image(R.drawable.image12);
        n4.setNews_title("Afghanistan is developed country");
        n4.setAuthor("Ahmadullah Ahmadi");

        List<Blogs> newsList = new ArrayList<>();
        newsList.add(n1);
        newsList.add(n2);
        newsList.add(n3);
        newsList.add(n4);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BlogsRecyclerAdapter(this, newsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(BlogsActivity.this,BlogsContentActivity.class);
            Blogs news = adapter.getItem(position);
            intent.putExtra("title",news.getNews_title());
            intent.putExtra("image",news.getNews_image());
            intent.putExtra("content",news.getNews_content());
            intent.putExtra("author",news.getAuthor());
            startActivity(intent);
           // Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        sharedPref = getSharedPreferences("languagePref",Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        if(language.equals("pashto")){
            getMenuInflater().inflate(R.menu.p_back_menu, menu);
        }else if (language.equals("dari")){
            getMenuInflater().inflate(R.menu.p_back_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.back_menu, menu);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_back) {
            finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
    public void openProfileActivity(View view) {
        Intent intent = new Intent(BlogsActivity.this,ProfileActivity.class);
        startActivity(intent);
    }



}