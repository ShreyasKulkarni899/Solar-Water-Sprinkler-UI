package com.nkocet.untitled;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Card> cards;
    FloatingActionButton add;
    SharedPreferences preferences;
    LinearLayout greetingCard;
    TextView greetText1, greetText2;
    RecyclerViewAdapter adapter;
    Database database;
    int nightModeFlag;
    static int DONE = 1;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        greetingCard = view.findViewById(R.id.greetingCard);
        greetText1 = view.findViewById(R.id.greetText1);
        greetText2 = view.findViewById(R.id.greetText2);

        greetText1.setText("Hello, " + preferences.getString("name", null));
        greetText2.setText("Weather Cloudy");
        database = new Database(getContext());

        cards = new ArrayList<>();

        nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        cards = database.toggleDarkMode(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getActivity(), cards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        add = view.findViewById(R.id.addDeviceFAB);
        add.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), AddDevice.class), 2));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            cards = database.toggleDarkMode(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);
            adapter.updateReceiptsList(cards);
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }
}