package com.anwari.myculture;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devlomi.record_view.OnBasketAnimationEnd;
import com.devlomi.record_view.OnRecordClickListener;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordPermissionHandler;
import com.devlomi.record_view.RecordView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class IdeasActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    TextView editPost;
    ImageButton imageFloatingButton;

    private AudioRecorder audioRecorder;
    private File recordFile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        boolean flag = false;
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_ideas_navigation);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_ideas_navigation);
        }
        else{
            setContentView(R.layout.english_ideas_navigation);
        }
       /* imageFloatingButton = findViewById(R.id.imagefloatingActionButton);
        imageFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(IdeasActivity.this)
                        .crop()//Crop image(Optional), Check Customization for more option
                        .cropSquare()
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });*/



        editPost = findViewById(R.id.editTextTextMultiLine);
        getIntent().getBooleanExtra("flag",false);

      if(flag) {
          if (!getIntent().getStringExtra("post").equals(null)) {
              editPost.setText(getIntent().getStringExtra("post"));
          }
      }
        //setContentView(R.layout.english_ideas_navigation);
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
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                if(item.getItemId() == R.id.home){
                    Intent intent = new Intent(IdeasActivity.this,NewsActivity.class);
                    startActivity(intent);
                }else if(item.getItemId() == R.id.idea){
                    Intent intent = new Intent(IdeasActivity.this,IdeasActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.entertainment){
                    Intent intent = new Intent(IdeasActivity.this,EntertainmentActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.about){
                    Intent intent = new Intent(IdeasActivity.this,AboutActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.logout){
                    Intent intent = new Intent(IdeasActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        // recorder
                audioRecorder = new AudioRecorder();

        RecordView recordView = (RecordView) findViewById(R.id.record_view);
        recordView.setSlideToCancelTextColor(Color.parseColor("#E12C9F"));
        final RecordButton recordButton = (RecordButton) findViewById(R.id.record_button);
        //Button btnChangeOnclick = (Button) findViewById(R.id.btn_change_onclick);

        //IMPORTANT
        recordButton.setRecordView(recordView);

        // if you want to click the button (in case if you want to make the record button a Send Button for example..)
//        recordButton.setListenForRecord(false);

        /*btnChangeOnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recordButton.isListenForRecord()) {
                    recordButton.setListenForRecord(false);
                    Toast.makeText(IdeasActivity.this, "onClickEnabled", Toast.LENGTH_SHORT).show();
                } else {
                    recordButton.setListenForRecord(true);
                    Toast.makeText(IdeasActivity.this, "onClickDisabled", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        //ListenForRecord must be false ,otherwise onClick will not be called
        recordButton.setOnRecordClickListener(new OnRecordClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IdeasActivity.this, "RECORD BUTTON CLICKED", Toast.LENGTH_SHORT).show();
                Log.d("RecordButton", "RECORD BUTTON CLICKED");
            }
        });


        //Cancel Bounds is when the Slide To Cancel text gets before the timer . default is 8
        recordView.setCancelBounds(8);


        recordView.setSmallMicColor(Color.parseColor("#c2185b"));

        //prevent recording under one Second
        recordView.setLessThanSecondAllowed(false);


        recordView.setSlideToCancelText("Slide To Cancel");


        recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);


        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                recordFile = new File(getFilesDir(), UUID.randomUUID().toString() + ".3gp");
                try {
                    audioRecorder.start(recordFile.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("RecordView", "onStart");
                Toast.makeText(IdeasActivity.this, "OnStartRecord", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                stopRecording(true);

                Toast.makeText(IdeasActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

                Log.d("RecordView", "onCancel");

            }

            @Override
            public void onFinish(long recordTime, boolean limitReached) {
                stopRecording(false);


                String time = getHumanTimeText(recordTime);
                Toast.makeText(IdeasActivity.this, "onFinishRecord - Recorded Time is: " + time + " File saved at " + recordFile.getPath(), Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onFinish" + " Limit Reached? " + limitReached);
                Log.d("RecordTime", time);
            }

            @Override
            public void onLessThanSecond() {
                stopRecording(true);

                Toast.makeText(IdeasActivity.this, "OnLessThanSecond", Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onLessThanSecond");
            }
        });


        recordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                Log.d("RecordView", "Basket Animation Finished");
            }
        });

        recordView.setRecordPermissionHandler(new RecordPermissionHandler() {
            @Override
            public boolean isPermissionGranted() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return true;
                }

                boolean recordPermissionAvailable = ContextCompat.checkSelfPermission(IdeasActivity.this, Manifest.permission.RECORD_AUDIO) == PERMISSION_GRANTED;
                if (recordPermissionAvailable) {
                    return true;
                }


                ActivityCompat.
                        requestPermissions(IdeasActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                0);

                return false;

            }
        });


    }

    private void stopRecording(boolean deleteFile) {
        audioRecorder.stop();
        if (recordFile != null && deleteFile) {
            recordFile.delete();
        }
    }


    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
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

    public void openNews(View view) {
        Intent i = new Intent(IdeasActivity.this,NewsActivity.class);
        startActivity(i);
    }

    public void openRecentIdeas(View view) {
        Intent intent = new Intent(IdeasActivity.this,RecentPostsActivity.class);
        startActivity(intent);
    }
    public void openProfileActivity(View view) {
        Intent intent = new Intent(IdeasActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    public void openGallery(View view) {
        //Intent intent = new Intent(IdeasActivity.this,Intent.ACTION_GET_CONTENT);
    }

    public void openImage(View view) {
        ImagePicker.with(IdeasActivity.this)
                .crop()//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
}