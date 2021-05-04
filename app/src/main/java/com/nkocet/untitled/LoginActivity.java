package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText nameTextView, phoneTextView, emailTextView, passwordTextView;
    MaterialButton proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        proceed = findViewById(R.id.proceed2);

        // TODO: 24/4/21 Validation code here

        proceed.setOnClickListener(v -> {
            if (TextUtils.isEmpty(nameTextView.getText())) {
                nameTextView.setError("This field cannot be left empty!");
                nameTextView.requestFocus();
            } else if (TextUtils.isEmpty(phoneTextView.getText())) {
                phoneTextView.setError("This field cannot be left empty!");
                phoneTextView.requestFocus();
            } else if (TextUtils.isEmpty(emailTextView.getText())) {
                emailTextView.setError("This field cannot be left empty!");
                emailTextView.requestFocus();
            } else if (TextUtils.isEmpty(passwordTextView.getText())) {
                passwordTextView.setError("This field cannot be left empty!");
                passwordTextView.requestFocus();
            } else {
                if (phoneTextView.getText().length() != 10) {
                    phoneTextView.setError("Invalid phone number!");
                    phoneTextView.requestFocus();
                } else if (passwordTextView.getText().length() != 4) {
                    passwordTextView.setError("Password length must be 4!");
                    passwordTextView.forceLayout();
                } else {
                    SharedPreferences.Editor preferences = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
                    preferences.putString("name", nameTextView.getText().toString())
                            .putString("phone", phoneTextView.getText().toString())
                            .putString("email", emailTextView.getText().toString())
                            .putString("pin", passwordTextView.getText().toString())
                            .putBoolean("isFirstTime", false)
                            .apply();
                    Log.d("Login Activity", "onCreate: Done saving!");

                    startActivity(new Intent(LoginActivity.this, Dashboard.class));
                    finish();
                }
            }
        });
    }
}