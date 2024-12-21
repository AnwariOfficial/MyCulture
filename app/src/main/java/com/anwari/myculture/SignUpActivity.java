package com.anwari.myculture;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText;
    SharedPreferences sharedPref;
    Button signup;
    boolean flags = false;
    EditText username,surname,password,confirmPassword,gender,province,marital_status,email;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("languagePref", Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.defautllanguage);
        String language = sharedPref.getString(getString(R.string.language), defaultValue);
        //super.onCreate(savedInstanceState);
        if(language.equals("pashto")){
            setContentView(R.layout.pashto_activity_sign_up);
        }else if (language.equals("dari")){
            setContentView(R.layout.dari_activity_sign_up);
        }
        else{
            setContentView(R.layout.english_activity_sign_up);
        }
        signup = findViewById(R.id.signUp_button);
        //setContentView(R.layout.english_activity_sign_up);
         username = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        gender = findViewById(R.id.gender);
        marital_status = findViewById(R.id.marital_status);
        province = findViewById(R.id.province);
        email = findViewById(R.id.email);

            eText=(EditText) findViewById(R.id.date_of_birth);
            eText.setInputType(InputType.TYPE_NULL);
            eText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(SignUpActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });

            username.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(username.getText().toString().isEmpty()) {
                        username.setError("Field can not be empty");
                        flags = false;
                    }
                    else {
                        flags = true;
                    }
                }
            });
            surname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(surname.getText().toString().isEmpty()){
                        surname.setError("Field can not be empty");
                        flags = false;
                    }
                    else{
                        flags = true;
                    }
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
                    if(!validatePassword()){
                        flags = false;
                    }
                    else{
                        flags = true;
                    }
                }
            });
            confirmPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (confirmPassword.getText().toString().isEmpty()){
                        confirmPassword.setError("Field can not be empty");
                        flags = false;
                    }else {
                        flags = true;
                        confirmPassword.setError(null);
                    }
                    if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                        confirmPassword.setError("Password doesn't match with confirm password");
                        flags = false;
                    }
                    else{
                        flags =true;
                        confirmPassword.setError(null);
                    }
                }
            });
            eText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(eText.getText().toString().isEmpty()){
                        // eText.setError("Field can not be empty");
                        flags = false;
                    }
                    else {
                        flags=true;
                        eText.setError(null);
                    }
                }
            });
            gender.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(gender.getText().toString().isEmpty()){
                        gender.setError("Field can not be empty");
                        flags = false;
                    }else{
                        flags=true;
                        gender.setError(null);
                    }

                }
            });
            gender.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!(gender.getText().toString().trim().equalsIgnoreCase("Male") ||gender.getText().toString().trim().equalsIgnoreCase("female"))){
                        gender.setError("Only Male or Female");
                        flags = false;
                    }
                    else{
                        flags =true;
                        gender.setError(null);
                    }
                }
            });
            marital_status.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(!(marital_status.getText().toString().trim().equalsIgnoreCase("single")||
                            marital_status.getText().toString().trim().equalsIgnoreCase("Married")||
                            marital_status.getText().toString().trim().equalsIgnoreCase("divorce "))){
                        marital_status.setError("Single, Married or Divorce");
                        flags = false;
                    }
                    else {
                        marital_status.setError(null);
                        flags = true;
                    }
                }
            });
        province.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(province.getText().toString().isEmpty()){
                    province.setError("Field can not be empty");
                    flags = false;
                }
                else{
                    flags = true;
                    province.setError(null);
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!validateEmail()){
                    flags = false;
                }
                else{
                    flags = true;
                }
            }
        });

            signup.setEnabled(flags);
    }
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

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

    }
}