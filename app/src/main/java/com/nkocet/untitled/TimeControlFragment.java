package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TimeControlFragment extends Fragment implements RecyclerViewAdapter.CardClickListener {

    RecyclerView recyclerView;
    static int UPDATE_RECYCLER_VIEW = 1;
    RecyclerViewAdapter adapter;
    Database database;
    int nightModeFlag;
    SwipeRefreshLayout refreshLayout;
    SharedPreferences preferences;
    ArrayList<Card> cards;
    FloatingActionButton add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_control, container, false);
        preferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        database = new Database(requireContext());
        refreshLayout = view.findViewById(R.id.refresh);

        cards = new ArrayList<>();

        nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        cards = database.getTimeControlCards(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);

        Toast.makeText(requireContext(), String.valueOf(cards.size()), Toast.LENGTH_SHORT).show();

        recyclerView = view.findViewById(R.id.timeControlRecyclerView);
        adapter = new RecyclerViewAdapter(requireContext(), cards, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCards();
                refreshLayout.setRefreshing(false);
            }
        });

        add = view.findViewById(R.id.addToTimeControl);
        add.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), AddDevice.class), UPDATE_RECYCLER_VIEW));
        return view;
    }

    private void updateCards() {
        nightModeFlag = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        cards = database.getTimeControlCards(nightModeFlag == Configuration.UI_MODE_NIGHT_YES);
        adapter.updateCards(cards);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_RECYCLER_VIEW) updateCards();
    }

    @Override
    public void onCardClick(int position) {
        startActivityForResult(new Intent(getContext(), EditActivity.class).putExtra("card", cards.get(position)), UPDATE_RECYCLER_VIEW);
    }
}