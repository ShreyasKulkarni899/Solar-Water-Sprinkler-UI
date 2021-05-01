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
import com.google.android.material.timepicker.MaterialTimePicker;

public class EditActivity extends AppCompatActivity {

    TextInputEditText name, location, rate, start, end;
    Slider slider;
    Chip SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    MaterialTimePicker timePicker;
    Vibrator vibrator;
    SharedPreferences preferences;
    ImageView status, back;
    TextView title;
    SwitchMaterial auto;

    MaterialButton save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = findViewById(R.id.editDeviceName);
        location = findViewById(R.id.editDeviceLocation);
        rate = findViewById(R.id.editFlowRate);
        slider = findViewById(R.id.flowRateSlider);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        start = findViewById(R.id.editStartTime);
        end = findViewById(R.id.editEndTime);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);
        title = findViewById(R.id.sprinklerName);
        auto = findViewById(R.id.auto);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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
        status.setImageResource(sprinkler.status == Sprinkler.ONLINE
                ? R.drawable.ic_baseline_online_24
                : R.drawable.ic_baseline_offline_24);
        title.setText(card.name);

        back.setOnClickListener(v -> finish());

        auto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rate.setEnabled(!isChecked);
            slider.setEnabled(!isChecked);
        });

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean haptics = preferences.getBoolean("haptics", true);

        // TODO: This part of code has bugs as of now.
        timePicker = new MaterialTimePicker.Builder().setTitleText("Choose time").build();

        start.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) timePicker.show(getSupportFragmentManager(), "Time Picker");
        });
        start.setOnClickListener(v -> timePicker.show(getSupportFragmentManager(), "Time picker"));

        end.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) timePicker.show(getSupportFragmentManager(), "Time Picker");
        });
        end.setOnClickListener(v -> timePicker.show(getSupportFragmentManager(), "Time picker"));
        // TODO: End

        cancel.setOnClickListener(v -> finish());
        slider.addOnChangeListener((slider, value, fromUser) -> {
            if (value % 5 == 0 && haptics) vibrator.vibrate(30);
            rate.setText(String.valueOf(value));
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

        if (sprinkler.activeDays[0] == 1) SUNDAY.setChecked(true);
        if (sprinkler.activeDays[1] == 1) MONDAY.setChecked(true);
        if (sprinkler.activeDays[2] == 1) TUESDAY.setChecked(true);
        if (sprinkler.activeDays[3] == 1) WEDNESDAY.setChecked(true);
        if (sprinkler.activeDays[4] == 1) THURSDAY.setChecked(true);
        if (sprinkler.activeDays[5] == 1) FRIDAY.setChecked(true);
        if (sprinkler.activeDays[6] == 1) SATURDAY.setChecked(true);

        save.setOnClickListener(v -> {
            // TODO: (data validation required) Save current state and push to sever
            finish();
        });
    }
}