package com.nkocet.untitled;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class AddDevicesSetup extends AppCompatActivity implements CodeDialogBox.DialogBoxListener {

    MaterialCardView addDevCard;
    MaterialButton proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices_setup);

        addDevCard = findViewById(R.id.addDevice);
        proceed = findViewById(R.id.proceed3);

        addDevCard.setOnClickListener(v -> {
            // Dialog box asking to enter 6 digit code, pops up
            CodeDialogBox codeDialogBox = new CodeDialogBox();
            codeDialogBox.show(getSupportFragmentManager(), "Add devices");
        });

        proceed.setOnClickListener(v -> {
            // FIXME: 25/04/21 Validation of data required before proceeding
            startActivity(new Intent(AddDevicesSetup.this, Dashboard.class));
        });
    }

    @Override
    public void getCode(String code) {
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
    }
}