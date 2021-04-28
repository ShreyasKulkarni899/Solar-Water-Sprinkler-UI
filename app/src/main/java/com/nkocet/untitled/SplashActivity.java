package com.nkocet.untitled;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SplashActivity extends AppCompatActivity {

    MaterialButton proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        proceed = findViewById(R.id.proceed1);
        Log.d("SA", "onCreate: Splash Activity");
        proceed.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        });
    }
}