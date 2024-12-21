package com.anwari.myculture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class ForgetPasswordActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    String url="http://ahmadzai-001-site1.btempurl.com/api/auth/SendEmail";
    EditText forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_activity_foget_password);


        //  setContentView(R.layout.activity_news);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_activity_foget_password);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_activity_foget_password);
        }
        else{
            setContentView(R.layout.english_activity_foget_password);
        }
        forgetPassword = findViewById(R.id.forgetPassword);
    }

    public void getRecentPosts() {
        //final ProgressDialog loading = new ProgressDialog(ForgetPasswordActivity.this, R.style.AppCompatAlertDialogStyle);
//        loading.setMessage("Please Wait...");
//        loading.setCanceledOnTouchOutside(false);
//        loading.show();
//        String email = forgetPassword.getText().toString();
       // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        /*StringRequest request = new StringRequest(Request.Method.POST, url, email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error",error.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Interal Error Occur.. ",Toast.LENGTH_LONG).show();
            }
        });*/
        //queue.add(request);

    }
}