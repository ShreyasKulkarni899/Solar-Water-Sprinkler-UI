package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Vibrator;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.concurrent.Executor;

public class AuthenticationActivity extends AppCompatActivity {

    TextInputEditText pin;
    MaterialButton button;
    SharedPreferences preferences;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        /* This activity is responsible for locking the application with biometrics
         * This part of code is executed only if user has turned on app-lock from settings */

        button = findViewById(R.id.login);
        pin = findViewById(R.id.password);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // TODO: 25/4/21 Biometric Authentication code here
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                    .setTitle("App is locked")
                    .setSubtitle("Authenticate with your fingerprint")
                    .setNegativeButton("Cancel", executor, (dialog, which) -> {
                    })
                    .build();
            biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    finish();
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    super.onAuthenticationHelp(helpCode, helpString);
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    startActivity(new Intent(AuthenticationActivity.this, Dashboard.class));
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });
        }

        button.setOnClickListener(v -> {
            String password = Objects.requireNonNull(pin.getText()).toString();
            if (TextUtils.isEmpty(password)) pin.setError("This field cannot be left empty!");
            else if (password.length() != 4) pin.setError("PIN must be of length 4");
            else {
                if (password.equals(preferences.getString("pin", null))) {
                    vibrator.vibrate(new long[]{0, 10, 50, 60, 100}, -1);
                    startActivity(new Intent(AuthenticationActivity.this, Dashboard.class));
                    finish();
                } else {
                    pin.setError("Invalid password");
                    vibrator.vibrate(new long[]{0, 200, 100, 200}, -1);
                }
            }
        });
    }
}