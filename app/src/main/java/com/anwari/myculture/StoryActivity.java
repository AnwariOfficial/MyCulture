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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

public class StoryActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    ImageView imageView;
    ImageView photo;
    TextView storyTeller;
    TextView story;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.english_story_navigation);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_story_navigation);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_story_navigation);
        }
        else{
            setContentView(R.layout.english_story_navigation);
        }
        photo = findViewById(R.id.imageStoryteller);
        imageView = findViewById(R.id.image);
        storyTeller = findViewById(R.id.storyTellerName);
        story = findViewById(R.id.text_story);
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
                    Intent intent = new Intent(StoryActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(StoryActivity.this,IdeasActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(StoryActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(StoryActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(StoryActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(getResources().getString(R.string.poem))
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String languageCode) {
                                if (languageCode.equals("ps") || languageCode.equals("fa")) {
                                    story.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    story.setTextDirection(View.TEXT_DIRECTION_RTL);
                                    story.setPadding(0,0,8,0);
                                } else if(languageCode.equals("en")) {
                                    story.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    story.setTextDirection(View.TEXT_DIRECTION_LTR);
                                    story.setPadding(8,0,0,0);
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
    imageView.setImageResource(R.drawable.story_icon);
    photo.setImageResource(R.drawable.image7);
    storyTeller.setText("Nazanin, 27");
    story.setText(R.string.poem);

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
        Intent intent = new Intent(StoryActivity.this,ProfileActivity.class);
        startActivity(intent);
    }
}