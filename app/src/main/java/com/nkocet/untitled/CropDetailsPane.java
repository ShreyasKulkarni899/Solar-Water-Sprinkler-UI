package com.nkocet.untitled;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CropDetailsPane extends AppCompatActivity {

    // Variable declarations
    TextView name, description;
    ImageView imageView;
    ImageButton back, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details_pane);

        // Variable initialisations
        name = findViewById(R.id.crop_name);
        description = findViewById(R.id.crop_description);
        back = findViewById(R.id.back);
        share = findViewById(R.id.share);

        // Setting initial data
        ListModel model = (ListModel) getIntent().getSerializableExtra("object");
        name.setText(model.title);
        description.setText(model.description);

        // OnClickListeners
        back.setOnClickListener(v -> finish());
        share.setOnClickListener(v -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show());

    }
}