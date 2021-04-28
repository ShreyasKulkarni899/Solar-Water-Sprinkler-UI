package com.nkocet.untitled;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Card> cards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cards = new ArrayList<>();

        cards.add(new Card("Sprinkler 1", "Lawn", R.color.purple_200, R.color.purple_700));
        cards.add(new Card("Sprinkler 2", "Home", R.color.purple_200, R.color.purple_700));
        cards.add(new Card("Sprinkler 3", "Farm", R.color.purple_200, R.color.purple_700));
        cards.add(new Card("Sprinkler 4", "Lawn", R.color.purple_200, R.color.purple_700));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(), cards));

        return view;
    }
}