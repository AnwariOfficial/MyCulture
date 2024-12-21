package com.anwari.myculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class SurveysActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_survey_navigation);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_survey_navigation);
        }
        else{
            setContentView(R.layout.english_survey_navigation);
        }
        //setContentView(R.layout.english_survey_navigation);

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
                    Intent intent = new Intent(SurveysActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(SurveysActivity.this,IdeasActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(SurveysActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(SurveysActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(SurveysActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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

    public void scrollLeft(View view) {
    }

    public void scrollRight(View view) {
    }
    public void openProfileActivity(View view) {
        Intent intent = new Intent(SurveysActivity.this,ProfileActivity.class);
        startActivity(intent);
    }
}