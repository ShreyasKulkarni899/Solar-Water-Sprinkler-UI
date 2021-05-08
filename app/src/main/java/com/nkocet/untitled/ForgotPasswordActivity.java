package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText phoneEditText, passwordEditText1, passwordEditText2;
    SharedPreferences preferences;
    MaterialButton reset;
    TextInputLayout container0, container1, container2;
    private static final String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phoneEditText = findViewById(R.id.reset_phone);
        passwordEditText1 = findViewById(R.id.resetPassword1);
        passwordEditText2 = findViewById(R.id.resetPassword2);
        container0 = findViewById(R.id.container0);
        container1 = findViewById(R.id.container1);
        container2 = findViewById(R.id.container2);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        reset = findViewById(R.id.reset);

        container1.setVisibility(View.GONE);
        container2.setVisibility(View.GONE);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (Character character : s.toString().toCharArray()) {
                        if (Character.isLetter(character)) {
                            container0.setStartIconDrawable(R.drawable.ic_baseline_alternate_email_24);
                            container0.setHint("Email");
                            break;
                        } else {
                            container0.setStartIconDrawable(R.drawable.ic_baseline_phone_24);
                            container0.setHint("Phone");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reset.setOnClickListener(v -> {
            String phone = preferences.getString("phone", null),
                    email = preferences.getString("email", null);
            if (Objects.requireNonNull(phoneEditText.getText()).toString().equals(phone) || Objects.requireNonNull(phoneEditText.getText()).toString().equals(email)) {
                container1.setVisibility(View.VISIBLE);
                container2.setVisibility(View.VISIBLE);
                reset.setText("Reset");
            } else {
                phoneEditText.setError("Not found!");
            }

            if (container1.getVisibility() == View.VISIBLE && container2.getVisibility() == View.VISIBLE) {
                String pin1 = Objects.requireNonNull(passwordEditText1.getText()).toString(),
                        pin2 = Objects.requireNonNull(passwordEditText2.getText()).toString();
                if (TextUtils.isEmpty(pin1))
                    passwordEditText1.setError("This field cannot be left empty!");
                else if (TextUtils.isEmpty(pin2))
                    passwordEditText2.setError("This field cannot be left empty!");
                else {
                    if (pin1.equals(pin2)) {
                        preferences.edit().putString("pin", pin1).apply();
                        Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    } else passwordEditText2.setError("Passwords don't match");
                }
            }
        });
    }
}