package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class AddDevice extends AppCompatActivity {

    TextInputEditText nameEditText, locationEditText, rateEditText, startEditText, endEditText;
    Slider slider;
    Chip SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    Vibrator vibrator;
    SharedPreferences preferences;
    ImageView status, back;
    TextView title;
    SwitchMaterial auto;
    MaterialButton add, cancel;
    Boolean haptics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        nameEditText = findViewById(R.id.editDeviceName);
        locationEditText = findViewById(R.id.editDeviceLocation);
        rateEditText = findViewById(R.id.editFlowRate);
        slider = findViewById(R.id.flowRateSlider);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
        startEditText = findViewById(R.id.editStartTime);
        endEditText = findViewById(R.id.editEndTime);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);
        title = findViewById(R.id.sprinklerName);
        auto = findViewById(R.id.auto);

        Database database = new Database(getApplicationContext());

        SUNDAY = findViewById(R.id.day1);
        MONDAY = findViewById(R.id.day2);
        TUESDAY = findViewById(R.id.day3);
        WEDNESDAY = findViewById(R.id.day4);
        THURSDAY = findViewById(R.id.day5);
        FRIDAY = findViewById(R.id.day6);
        SATURDAY = findViewById(R.id.day7);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        haptics = preferences.getBoolean("haptics", true);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        back.setOnClickListener(v -> finish());
        cancel.setOnClickListener(v -> finish());

        auto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rateEditText.setEnabled(!isChecked);
            slider.setEnabled(!isChecked);
        });

        slider.addOnChangeListener((slider, value, fromUser) -> {
            if (value % 5 == 0 && haptics) vibrator.vibrate(30);
            rateEditText.setText(String.valueOf((int) value));
        });

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                slider.setTrackHeight(50);
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                slider.setTrackHeight(30);
            }
        });

        add.setOnClickListener(v -> {
            // TODO: (data validation required) Save current state and push to sever
            database.getLastId();
            String name = nameEditText.getText().toString(),
                    location = locationEditText.getText().toString(),
                    rate = rateEditText.getText().toString();

            int[] activeDays = {SUNDAY.isChecked() ? 1 : 0,
                    MONDAY.isChecked() ? 1 : 0,
                    TUESDAY.isChecked() ? 1 : 0,
                    WEDNESDAY.isChecked() ? 1 : 0,
                    THURSDAY.isChecked() ? 1 : 0,
                    FRIDAY.isChecked() ? 1 : 0,
                    SATURDAY.isChecked() ? 1 : 0};

            Sprinkler sprinkler = new Sprinkler(1, Integer.parseInt(rate), activeDays, auto.isChecked());
            Card card = new Card(database.getLastId(), name, location, new String[]{"", "", ""}, sprinkler);
            database.insertData(card);
            setResult(2);
            finish();
        });
    }
}