package com.nkocet.untitled;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class HelpDetailsPane extends AppCompatActivity {

    TextView header, description;
    ImageView imageView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_pane);

        header = findViewById(R.id.headerTextHelpInner);
        description = findViewById(R.id.descriptionHelpInner);
        back = findViewById(R.id.backHelp);


        back.setOnClickListener(v -> finish());
        HelpModel model = (HelpModel) getIntent().getSerializableExtra("object");

        header.setText(model.headerTextHelp);
        description.setText(model.descriptionHelp);

    }
}
