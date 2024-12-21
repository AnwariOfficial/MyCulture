package com.anwari.myculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentPostsActivity extends AppCompatActivity {
    IdeaRecyclerAdapter adapter;
    Date date;
    SharedPreferences sharedPref;
    private String url;
    List<Idea> newsList;
    String profileImageUrl;
    RecyclerView recyclerView;
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_recent_post_navigation);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.navigation_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.stopen, R.string.stclose);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        newsList = new ArrayList<>();
        url = getResources().getString(R.string.baseUrl);
        //getRecentPosts();


         profileImage = findViewById(R.id.profileImage);
         recyclerView = findViewById(R.id.recentIdeasRecyclerView);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new IdeaRecyclerAdapter(this, newsList);
        //recyclerView.setAdapter(adapter);


        Idea i1 = new Idea();
        i1.setUserName("Najibullah Anwari");
        i1.setPostDate(new Date());
        i1.setIdeaPost(getResources().getString(R.string.news4));
        i1.setProfilePhoto(R.drawable.anwari);

        Idea i2 = new Idea();
        i2.setUserName("Ehsanullah Sadiq");
        i2.setPostDate(new Date());
        i2.setIdeaPost(getResources().getString(R.string.lgContentNews));
        i2.setProfilePhoto(R.drawable.photo_placeholder);

        Idea i3 = new Idea();
        i3.setUserName("Rafiullah Omar");
        i3.setPostDate(new Date());
        i3.setIdeaPost(getResources().getString(R.string.news1));
        i3.setProfilePhoto(R.drawable.profile);

        newsList.add(i1);
        newsList.add(i2);
        newsList.add(i3);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    Intent intent = new Intent(RecentPostsActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(RecentPostsActivity.this,IdeasActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(RecentPostsActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(RecentPostsActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(RecentPostsActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new IdeaRecyclerAdapter(getApplicationContext(), newsList);
        recyclerView.setAdapter(adapter);

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
    public void openProfileActivity(View view) {
        Intent intent = new Intent(RecentPostsActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    //Fetching Data

   /* // Get Request For JSONObject
    public void getRecentPosts() {
        final ProgressDialog loading = new ProgressDialog(RecentPostsActivity.this, R.style.AppCompatAlertDialogStyle);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url+"api/home/GetIdea/1", null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonIdea = response.getJSONObject(i);
                        Idea idea2 = new Idea();
                        idea2.setProfilePhoto(jsonIdea.getString("userImage"));
                        idea2.setUserName(jsonIdea.getString("name"));
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(jsonIdea.getString("postedDate"));
                        idea2.setPostDate(date1);
                        idea2.setIdeaPost(jsonIdea.getString("description"));
                        profileImageUrl = jsonIdea.getString("userImage");
                        newsList.add(idea2);
                        Log.d("Information", idea2.getIdeaPost());
                    }
                    Picasso.get().load(profileImageUrl).into(profileImage);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new IdeaRecyclerAdapter(getApplicationContext(), newsList);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(),"Error  "+e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Log.d("Volley Error",error.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Interal ResponseError Occur.. "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);

    }*/


}