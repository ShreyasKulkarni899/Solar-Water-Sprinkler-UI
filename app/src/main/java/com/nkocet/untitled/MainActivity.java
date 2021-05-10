package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Variable initialisations
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true),
                darkMode = preferences.getBoolean("darkMode", false),
                bioAuth = preferences.getBoolean("bioAuth", false);

        if (darkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (isFirstTime)
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        else if (bioAuth)
            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
        else
            startActivity(new Intent(MainActivity.this, Dashboard.class));
        finish();
    }
}