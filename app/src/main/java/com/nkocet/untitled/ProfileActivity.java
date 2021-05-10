package com.nkocet.untitled;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    // Variable declarations
    TextInputEditText nameEditText, phoneEditText, emailEditText;
    SharedPreferences preferences;
    MaterialButton update;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Variable initialisations
        nameEditText = findViewById(R.id.editName);
        phoneEditText = findViewById(R.id.editPhone);
        emailEditText = findViewById(R.id.editEmail);
        back = findViewById(R.id.back);
        update = findViewById(R.id.update);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        // Assigning initial values
        nameEditText.setText(preferences.getString("name", "No name"));
        phoneEditText.setText(preferences.getString("phone", "0000000000"));
        emailEditText.setText(preferences.getString("email", "No email"));

        // OnClick Listeners
        update.setOnClickListener(v -> {
            String name = nameEditText.getText().toString(),
                    phone = phoneEditText.getText().toString(),
                    email = emailEditText.getText().toString();
            if (TextUtils.isEmpty(name)) nameEditText.setError("This field cannot be left empty!");
            else if (TextUtils.isEmpty(phone))
                phoneEditText.setError("This field cannot be left empty!");
            else if (TextUtils.isEmpty(email))
                emailEditText.setError("This field cannot be left empty!");
            else {
                preferences.edit()
                        .putString("name", name)
                        .putString("phone", phone)
                        .putString("email", email)
                        .apply();
                setResult(Dashboard.UPDATE_NAV_HEADER);
                finish();
            }
        });
        back.setOnClickListener(v -> finish());
    }
}