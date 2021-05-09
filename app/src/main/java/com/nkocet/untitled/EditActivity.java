package com.nkocet.untitled;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";

    MaterialButtonToggleGroup buttonToggleGroup;
    TextInputEditText nameEditText, locationEditText, rateEditText, startEditText, endEditText;
    Slider slider;
    Chip SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    MaterialTimePicker timePicker;
    Vibrator vibrator;
    SharedPreferences preferences;
    ImageView status, back;
    ImageButton deleteButton, power_off_button;
    TextView title;
    MaterialButton auto, manual;
    MaterialButton saveButton, cancelButton;
    Database database;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        database = new Database(getBaseContext());
        nameEditText = findViewById(R.id.editDeviceName);
        locationEditText = findViewById(R.id.editDeviceLocation);
        rateEditText = findViewById(R.id.editFlowRate);
        slider = findViewById(R.id.flowRateSlider);
        saveButton = findViewById(R.id.save);
        cancelButton = findViewById(R.id.cancel);
        startEditText = findViewById(R.id.editStartTime);
        endEditText = findViewById(R.id.editEndTime);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);
        title = findViewById(R.id.sprinklerName);
        auto = findViewById(R.id.auto);
        manual = findViewById(R.id.manual);
        deleteButton = findViewById(R.id.delete);
        power_off_button = findViewById(R.id.edit_power_off);
        buttonToggleGroup = findViewById(R.id.buttonGroup);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        SUNDAY = findViewById(R.id.day1);
        MONDAY = findViewById(R.id.day2);
        TUESDAY = findViewById(R.id.day3);
        WEDNESDAY = findViewById(R.id.day4);
        THURSDAY = findViewById(R.id.day5);
        FRIDAY = findViewById(R.id.day6);
        SATURDAY = findViewById(R.id.day7);

        Card card = (Card) getIntent().getSerializableExtra("card");

        nameEditText.setText(card.name);
        locationEditText.setText(card.location);
        rateEditText.setText(String.valueOf(card.sprinkler.rate));
        rateEditText.setEnabled(false);
        slider.setValue(card.sprinkler.rate);

        power_off_button.setOnClickListener(v -> {
            card.sprinkler.status = card.sprinkler.status == Sprinkler.ONLINE ? Sprinkler.OFFLINE : Sprinkler.ONLINE;
            status.setImageResource(card.sprinkler.status == Sprinkler.ONLINE
                    ? R.drawable.ic_baseline_online_24
                    : R.drawable.ic_baseline_offline_24);

            title.setText(card.sprinkler.status == Sprinkler.ONLINE
                    ? "Online"
                    : "Offline");
        });

        status.setImageResource(card.sprinkler.status == Sprinkler.ONLINE
                ? R.drawable.ic_baseline_online_24
                : R.drawable.ic_baseline_offline_24);

        title.setText(card.sprinkler.status == Sprinkler.ONLINE
                ? "Online"
                : "Offline");

        back.setOnClickListener(v -> finish());

        if (card.sprinkler.auto) auto.setChecked(true);
        else manual.setChecked(true);

        buttonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> slider.setEnabled(checkedId == manual.getId()));

        deleteButton.setOnClickListener(v -> {
            database.delete(card);
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean haptics = preferences.getBoolean("haptics", true);

        startEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showTimePicker(startEditText);
        });
        startEditText.setOnClickListener(v -> showTimePicker(startEditText));

        endEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showTimePicker(endEditText);
        });
        endEditText.setOnClickListener(v -> showTimePicker(endEditText));

        cancelButton.setOnClickListener(v -> finish());

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

        SUNDAY.setChecked(card.sprinkler.activeDays[0] == 1);
        MONDAY.setChecked(card.sprinkler.activeDays[1] == 1);
        TUESDAY.setChecked(card.sprinkler.activeDays[2] == 1);
        WEDNESDAY.setChecked(card.sprinkler.activeDays[3] == 1);
        THURSDAY.setChecked(card.sprinkler.activeDays[4] == 1);
        FRIDAY.setChecked(card.sprinkler.activeDays[5] == 1);
        SATURDAY.setChecked(card.sprinkler.activeDays[6] == 1);

        startEditText.setText(card.sprinkler.activeTime[0]);
        endEditText.setText(card.sprinkler.activeTime[1]);

        saveButton.setOnClickListener(v -> {
            // TODO: (data validation required) Save current state and push to sever

            String nameString = Objects.requireNonNull(nameEditText.getText()).toString(),
                    locationString = Objects.requireNonNull(locationEditText.getText()).toString();
            String[] colors = card.colors;

            int rateInt = (int) slider.getValue();
            int[] activeDays = {
                    SUNDAY.isChecked() ? 1 : 0,
                    MONDAY.isChecked() ? 1 : 0,
                    TUESDAY.isChecked() ? 1 : 0,
                    WEDNESDAY.isChecked() ? 1 : 0,
                    THURSDAY.isChecked() ? 1 : 0,
                    FRIDAY.isChecked() ? 1 : 0,
                    SATURDAY.isChecked() ? 1 : 0
            };

            String start = Objects.requireNonNull(startEditText.getText()).toString();
            String end = Objects.requireNonNull(endEditText.getText()).toString();

            Sprinkler sprinklerFinal;
            sprinklerFinal = new Sprinkler(card.sprinkler.status, rateInt, activeDays, new String[]{start, end}, auto.isChecked());
            Card finalCard = new Card(card.id, nameString, locationString, colors, sprinklerFinal);
            database.editCard(card, finalCard);
            setResult(HomeFragment.UPDATE_RECYCLER_VIEW);
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            database.delete(card);
            setResult(HomeFragment.UPDATE_RECYCLER_VIEW);
            finish();
        });
    }

    public void showTimePicker(TextInputEditText editText) {
        timePicker = new MaterialTimePicker.Builder()
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText("Choose time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();
        timePicker.show(getSupportFragmentManager(), "Time picker");
        timePicker.addOnPositiveButtonClickListener(v -> {
            String hours = String.valueOf(timePicker.getHour()),
                    minutes = String.valueOf(timePicker.getMinute());
            editText.setText(String.format("%s:%s", hours, minutes));
        });
    }
}