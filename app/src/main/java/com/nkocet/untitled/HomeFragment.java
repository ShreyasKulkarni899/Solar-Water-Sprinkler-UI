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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.CardClickListener {
    RecyclerView recyclerView;
    ArrayList<Card> cards;
    FloatingActionButton add;
    SharedPreferences preferences;
    LinearLayout greetingCard;
    TextView greetText1, greetText2;
    RecyclerViewAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    Database database;
    int nightModeFlag;
    static int UPDATE_RECYCLER_VIEW = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        greetingCard = view.findViewById(R.id.greetingCard);
        greetText1 = view.findViewById(R.id.greetText1);
        greetText2 = view.findViewById(R.id.greetText2);
        refreshLayout = view.findViewById(R.id.refresh);

        database = new Database(getContext());

        cards = new ArrayList<>();

        nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        cards = database.getCardsByDarkMode(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);

        updateGreetCard();

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getContext(), cards, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        refreshLayout.setOnRefreshListener(() -> {
            update();
            refreshLayout.setRefreshing(false);
        });

        add = view.findViewById(R.id.addDeviceFAB);
        add.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), AddDevice.class), UPDATE_RECYCLER_VIEW));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_RECYCLER_VIEW) update();
    }

    void update() {
        nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        cards = database.getCardsByDarkMode(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);
        adapter.updateCards(cards);
        updateGreetCard();
    }

    @SuppressLint("SetTextI18n")
    public void updateGreetCard() {
        if (cards.size() == 0) {
            greetText1.setText("It's empty here!");
            greetText2.setText("Try adding some devices");
        } else {
            greetText1.setText("Hello, " + preferences.getString("name", null));
            greetText2.setText("Rain expected in 30 min.");
        }
    }

    @Override
    public void onCardClick(int position) {
        startActivityForResult(new Intent(getContext(), EditActivity.class).putExtra("card", cards.get(position)), UPDATE_RECYCLER_VIEW);
    }
}