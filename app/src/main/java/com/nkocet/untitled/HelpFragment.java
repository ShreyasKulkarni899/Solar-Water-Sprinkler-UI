package com.nkocet.untitled;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HelpFragment extends Fragment {

    ArrayList<HelpModel> helpNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        helpNote = new ArrayList<>();

        helpNote.add(new HelpModel("Add Device", getString(R.string.lorem_ipsum)));
        helpNote.add(new HelpModel("Crop Guide", getString(R.string.lorem_ipsum)));
        helpNote.add(new HelpModel("Setting Guide", getString(R.string.lorem_ipsum)));
        // Similarly add more objects to list
        // Or fetch from database

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHelp);
        HelpAdapter adapter = new HelpAdapter(getContext(), helpNote);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}