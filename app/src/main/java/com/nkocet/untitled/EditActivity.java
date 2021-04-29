package com.nkocet.untitled;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {

    TextInputEditText name, location, rate;
    Slider slider;
    Chip SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    MaterialButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = findViewById(R.id.editDeviceName);
        location = findViewById(R.id.editDeviceLocation);
        rate = findViewById(R.id.editFlowRate);
        slider = findViewById(R.id.flowRateSlider);
        save = findViewById(R.id.save);

        SUNDAY = findViewById(R.id.day1);
        MONDAY = findViewById(R.id.day2);
        TUESDAY = findViewById(R.id.day3);
        WEDNESDAY = findViewById(R.id.day4);
        THURSDAY = findViewById(R.id.day5);
        FRIDAY = findViewById(R.id.day6);
        SATURDAY = findViewById(R.id.day7);

        Intent intent = getIntent();
        Card card = (Card) intent.getSerializableExtra("card");
        Sprinkler sprinkler = card.sprinkler;

        name.setText(card.name);
        location.setText(card.location);
        rate.setText(String.valueOf(sprinkler.rate));
        slider.setValue(sprinkler.rate);

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

        if (sprinkler.activeDays[0] == 1) SUNDAY.setChecked(true);
        if (sprinkler.activeDays[1] == 1) MONDAY.setChecked(true);
        if (sprinkler.activeDays[2] == 1) TUESDAY.setChecked(true);
        if (sprinkler.activeDays[3] == 1) WEDNESDAY.setChecked(true);
        if (sprinkler.activeDays[4] == 1) THURSDAY.setChecked(true);
        if (sprinkler.activeDays[5] == 1) FRIDAY.setChecked(true);
        if (sprinkler.activeDays[6] == 1) SATURDAY.setChecked(true);

        save.setOnClickListener(v -> {
            Log.d("TAG", SUNDAY.isChecked() + "SUNDAY");
            Log.d("TAG", MONDAY.isChecked() + "MONDAY");
            Log.d("TAG", TUESDAY.isChecked() + "TUESDAY");
            Log.d("TAG", WEDNESDAY.isChecked() + "WEDNESDAY");
            Log.d("TAG", THURSDAY.isChecked() + "THURSDAY");
            Log.d("TAG", FRIDAY.isChecked() + "FRIDAY");
            Log.d("TAG", SATURDAY.isChecked() + "SATURDAY");
        });
    }
}