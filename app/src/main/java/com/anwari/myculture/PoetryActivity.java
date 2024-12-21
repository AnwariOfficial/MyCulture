package com.anwari.myculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PoetryActivity extends AppCompatActivity implements PoetryRecyclerAdapter.ItemClickListener {
    PoetryRecyclerAdapter adapter;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_poetry_navigation);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_poetry_navigation);
        }
        else{
            setContentView(R.layout.english_poetry_navigation);
        }
      //  setContentView(R.layout.english_poetry_navigation);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.navigation_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.stopen, R.string.stclose);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        List<Poetry> newsList = new ArrayList<>();

        Poetry poetry = new Poetry();
        poetry.setUserName("Najibullah Anwari");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);
        poetry.setPostDate(date);

        poetry.setIdeaPost(getResources().getString(R.string.lgContentNews));
        poetry.setAuthor("Abdul Qahar Sadiqi");

        Poetry poetry1 = new Poetry();
        poetry1.setUserName("Najibullah Anwari");
         date = Calendar.getInstance().getTime();
         dateFormat = new SimpleDateFormat("yyyy-mm-dd");
         strDate = dateFormat.format(date);
        poetry1.setPostDate(date);
        poetry1.setIdeaPost(getResources().getString(R.string.lgContentNews));
        poetry1.setAuthor("Abdul Qahar Sadiqi");

        Poetry poetry2 = new Poetry();
        poetry2.setUserName("Najibullah Anwari");
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        strDate = dateFormat.format(date);
        poetry2.setPostDate(date);
        poetry2.setIdeaPost(getResources().getString(R.string.lgContentNews));
        poetry2.setAuthor("Abdul Qahar Sadiqi");

        Poetry poetry3 = new Poetry();
        poetry3.setUserName("Najibullah Anwari");
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        strDate = dateFormat.format(date);
        poetry3.setPostDate(date);
        poetry3.setAuthor("Abdul Qahar Sadiqi");
        poetry3.setIdeaPost(getResources().getString(R.string.lgContentNews));

        newsList.add(poetry);
        newsList.add(poetry1);
        newsList.add(poetry1);
        newsList.add(poetry1);
        newsList.add(poetry2);
        newsList.add(poetry2);
        newsList.add(poetry2);
        newsList.add(poetry3);
        newsList.add(poetry3);
        newsList.add(poetry3);

        RecyclerView recyclerView = findViewById(R.id.poetryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator( new DefaultItemAnimator());

        adapter = new PoetryRecyclerAdapter(this, newsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    Intent intent = new Intent(PoetryActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(PoetryActivity.this,IdeasActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(PoetryActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(PoetryActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(PoetryActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(PoetryActivity.this,PoetryContentActivity.class);
        Poetry news = adapter.getItem(position);
        intent.putExtra("username",news.getUserName());
        intent.putExtra("date", news.getPostDate().toString());
        intent.putExtra("poetry",news.getIdeaPost());
        intent.putExtra("author",news.getAuthor());
        startActivity(intent);
         //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

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
        Intent intent = new Intent(PoetryActivity.this,ProfileActivity.class);
        startActivity(intent);
    }
}