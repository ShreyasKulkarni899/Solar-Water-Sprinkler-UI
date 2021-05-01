package com.nkocet.untitled;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Card> cards;
    FloatingActionButton add;
    SharedPreferences preferences;
    LinearLayout greetingCard;
    TextView greetText1, greetText2;

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

        cards = new ArrayList<>();

        List<String[]> palette = new ArrayList<>();

        int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlag) {
            case Configuration.UI_MODE_NIGHT_YES:
                for (int i = 0; i < 6; i++)
                    palette.add(new String[]{"#404040", "#3B3B3B", "#FFFFFF"});
                greetText1.setTextColor(Color.parseColor("#35004A"));
                greetText2.setTextColor(Color.parseColor("#35004A"));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                palette.add(new String[]{"#FCF0F0", "#F8A9A9", "#FFFFFF"});
                palette.add(new String[]{"#EFFFF9", "#95FFD6", "#0A0057"});
                palette.add(new String[]{"#FFF0F7", "#FF98C8", "#FFFFFF"});
                palette.add(new String[]{"#EAF4FF", "#8BC1FF", "#FFFFFF"});
                palette.add(new String[]{"#CBE9FF", "#2A6B9B", "#FFFFFF"});
                palette.add(new String[]{"#EAFFF8", "#3D715F", "#FFFFFF"});
                break;
        }

        // Sample sprinkler_online
        Sprinkler sprinkler_online = new Sprinkler(Sprinkler.ONLINE, 15, new int[]{1, 0, 1, 0, 1, 0, 1});
        Sprinkler sprinkler_offline = new Sprinkler(Sprinkler.OFFLINE, 50, new int[]{0, 1, 0, 1, 1, 0, 0});


        cards.add(new Card("Sprinkler 1", "Home", palette.get(0), sprinkler_online));
        cards.add(new Card("Sprinkler 2", "Lawn", palette.get(1), sprinkler_offline));
        cards.add(new Card("Sprinkler 3", "Farm", palette.get(2), sprinkler_online));
        cards.add(new Card("Sprinkler 4", "Home", palette.get(3), sprinkler_online));
        cards.add(new Card("Sprinkler 5", "Lawn", palette.get(4), sprinkler_offline));
        cards.add(new Card("Sprinkler 6", "Farm", palette.get(5), sprinkler_online));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(), cards));

        add = view.findViewById(R.id.addDeviceFAB);
        add.setOnClickListener(v -> Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show());
        return view;
    }
}