package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferences preferences;
    SwitchPreference darkMode, bioAuth;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        Preference preference = findPreference("logout");
        if (preference != null) {
            preference.setOnPreferenceClickListener(preference1 -> {
                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE).edit().clear().apply();
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                getActivity().finish();
                return false;
            });
        }

        darkMode = findPreference("darkMode");
        darkMode.setChecked(preferences.getBoolean("darkMode", false));
        darkMode.setOnPreferenceClickListener(preference1 -> {
            if (darkMode.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkMode.setSummary("On");
                preferences.edit().putBoolean("darkMode", true).apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkMode.setSummary("Off");
                preferences.edit().putBoolean("darkMode", false).apply();
            }
            return false;
        });

        bioAuth = findPreference("bioAuth");
        bioAuth.setChecked(preferences.getBoolean("bioAuth", false));
        bioAuth.setOnPreferenceClickListener(preference1 -> {
            if (bioAuth.isChecked()) {
                bioAuth.setSummary("Enabled");
                preferences.edit().putBoolean("bioAuth", true).apply();
            } else {
                bioAuth.setSummary("Disabled");
                preferences.edit().putBoolean("bioAuth", false).apply();
            }
            return false;
        });



    }




}