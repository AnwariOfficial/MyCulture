package com.anwari.myculture;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    Button btn_login;
    private EditText email;
    private EditText password;
    String loginUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("languagePref",Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_activity_main);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_activity_main);
        }
        else{
            setContentView(R.layout.english_activity_main);

        }


        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setEnabled(false);
        //password = findViewById(R.id.password);
        loginUrl = this.getResources().getString(R.string.baseUrl);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            validatePassword();
            if(validatePassword() && validateEmail()){
                btn_login.setEnabled(true);
            }

            }
        });


    }
    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    // Referencing email and password



    private boolean validateEmail() {

        // Extract input from EditText
        String emailInput = email.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }

    else {
            password.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }


    }


    public void login(View view) {
       Intent intent = new Intent(MainActivity.this,NewsActivity.class);
        startActivity(intent);
       // postLoginData();
    }

    public void openSignUp(View view) {
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void pashtoAsLanguage(View view) {
        sharedPref = getSharedPreferences("languagePref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.language), "pashto");
        editor.apply();
        editor.commit();
       Intent intent = new Intent(MainActivity.this,MainActivity.class);
       startActivity(intent);
       finish();
    }

    public void dariAsLanaguage(View view) {
        sharedPref = getSharedPreferences("languagePref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.language), "dari");
        editor.apply();
        editor.commit();
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void englishAsLanguage(View view) {
        sharedPref = getSharedPreferences("languagePref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.language),"english" );
        editor.apply();
        editor.commit();
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openFogetPasswordActivity(View view) {
        Intent intent = new Intent(MainActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
    //Json data

    /*public void postLoginData(){
        final ProgressDialog loading = new ProgressDialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName",email.getText().toString());
            jsonObject.put("Password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl+"api/auth/login", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               loading.dismiss();
                try {
                   *//* String error = response.getString("httpStatus");
                    if(error.equals("")||error.equals(null)){

                    }else if(error.equals("OK")){*//*
                    JSONObject userDetails = response.getJSONObject("user");
                       if(!response.getString("token").equals(null) && userDetails.getString("username").equals(email.getText().toString())){
                           Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                           startActivity(intent);
                       }
                       else{
                           Log.d("username ","Username or password problem");
                       }

                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                    Log.d("Error Login ","Error in reponse");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
            //Log.d("Error ",error.getMessage());
                Toast.makeText(getApplicationContext(),"Invalid Username and Password",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);
    }*/
}