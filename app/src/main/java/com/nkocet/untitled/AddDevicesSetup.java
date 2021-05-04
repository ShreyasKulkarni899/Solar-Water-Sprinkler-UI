package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AddDevicesSetup extends AppCompatActivity /*implements CodeDialogBox.DialogBoxListener*/ {

    MaterialButton addDevice, proceed;
    RecyclerView recyclerView;
    SharedPreferences preferences;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices_setup);

        addDevice = findViewById(R.id.addDevice);
        proceed = findViewById(R.id.proceed3);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (preferences.getBoolean("darkMode", false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        recyclerView = findViewById(R.id.recycler_view_setup);

        ArrayList<Card> cards = new ArrayList<>();

//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setAdapter(new RecyclerViewAdapter(this, cards));

        addDevice.setOnClickListener(v -> {
            // Dialog box asking to enter 6 digit code, pops up
//            CodeDialogBox codeDialogBox = new CodeDialogBox();
//            codeDialogBox.show(getSupportFragmentManager(), "Add devices");
//            if (code != null) {
            // TODO: Connection code
            Intent intent = new Intent(this, AddDevice.class);
            intent.putExtra(code, "code");
            startActivity(intent);
//            }
        });

        proceed.setOnClickListener(v -> {
            // FIXME: 25/04/21 Validation of data required before proceeding
            startActivity(new Intent(AddDevicesSetup.this, Dashboard.class));
            finish();
        });
    }

//    @Override
//    public void getCode(String code) {
//        this.code = code;
//    }
}