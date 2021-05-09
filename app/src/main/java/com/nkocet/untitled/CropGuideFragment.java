package com.nkocet.untitled;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CropGuideFragment extends Fragment {
    RecyclerView recyclerView;
    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_guide, container, false);

        ArrayList<ListModel> crops = new ArrayList<>();
        crops.add(new ListModel(R.drawable.image_1, "Picture 1", "Demo description.\nlorem-ipsum ..."));
        crops.add(new ListModel(R.drawable.image_1, "Picture 2", "Demo description.\nlorem-ipsum ..."));
        crops.add(new ListModel(R.drawable.image_1, "Picture 3", "Demo description.\nlorem-ipsum ..."));
        crops.add(new ListModel(R.drawable.image_1, "Picture 4", "Demo description.\nlorem-ipsum ..."));
        crops.add(new ListModel(R.drawable.image_1, "Picture 5", "Demo description.\nlorem-ipsum ..."));
        crops.add(new ListModel(R.drawable.image_1, "Picture 6", "Demo description.\nlorem-ipsum ..."));
        // Similarly add more objects to list
        // Or fetch from database

        // Setting up the recyclerView adapter
        recyclerView = view.findViewById(R.id.lst_recyclerView);
        adapter = new ListAdapter(getContext(), crops);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}