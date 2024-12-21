package com.anwari.myculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    Date date;
    ProfileRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.english_profile_navigation);
            Toolbar toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            NavigationView navigationView = findViewById(R.id.navigation_view);
            DrawerLayout drawerLayout = findViewById(R.id.navigation_layout);

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.stopen, R.string.stclose);
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();


            List<Idea> newsList = new ArrayList<>();

            /*Idea idea1 = new Idea();
            idea1.setProfilePhoto(R.drawable.anwari);
            idea1.setUserName("Najibullah Anwari");
             date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            idea1.setPostDate(date);

            idea1.setIdeaPost(getResources().getString(R.string.lgContentNews));

            Idea idea2 = new Idea();
            idea2.setProfilePhoto(R.drawable.news_image);
            idea2.setUserName("Rafiullah  Omar");
            date = Calendar.getInstance().getTime();
            dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            strDate = dateFormat.format(date);
            idea2.setPostDate(date);
            idea2.setIdeaPost(getResources().getString(R.string.news_content));

            Idea idea3 = new Idea();
            idea3.setProfilePhoto(R.drawable.anwari);
            idea3.setUserName("Noor Ahmad Ahmadzai");
            date = Calendar.getInstance().getTime();
            dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            strDate = dateFormat.format(date);
            idea3.setPostDate(date);
            idea3.setIdeaPost(getResources().getString(R.string.lgContentNews));
            newsList.add(idea1);
            newsList.add(idea2);
            newsList.add(idea3);
            newsList.add(idea1);
*/
            RecyclerView recyclerView = findViewById(R.id.recentIdeasRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ProfileRecyclerAdapter(this, newsList);
            recyclerView.setAdapter(adapter);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.home){
                        Intent intent = new Intent(ProfileActivity.this,NewsActivity.class);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.idea){
                        Intent intent = new Intent(ProfileActivity.this,IdeasActivity.class);
                        intent.putExtra("flag",true);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.entertainment){
                        Intent intent = new Intent(ProfileActivity.this,EntertainmentActivity.class);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.about){
                        Intent intent = new Intent(ProfileActivity.this,AboutActivity.class);
                        startActivity(intent);
                    }
                    else if(item.getItemId() == R.id.logout){
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
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
    }

