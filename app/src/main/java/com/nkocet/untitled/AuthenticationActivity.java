package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean bioAuth = preferences.getBoolean("bioAuth", false);

        /* This activity is responsible for locking the application with biometrics
         * This part of code is executed only if user has turned on app-lock from settings */

        if (bioAuth) {
            // TODO: 25/4/21 Biometric Authentication code here
        } else {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        }
    }
}