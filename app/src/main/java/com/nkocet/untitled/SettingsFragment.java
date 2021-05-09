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
    SwitchPreference darkMode, bioAuth, haptics;
    Preference logout, appInfo, forgot;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        logout = findPreference("logout");
        if (logout != null) {
            logout.setOnPreferenceClickListener(preference1 -> {
                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE).edit().clear().apply();
                getContext().deleteDatabase("database.db");
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                return false;
            });
        }

        darkMode = findPreference("darkMode");
        boolean isDarkMode = preferences.getBoolean("darkMode", false);
        darkMode.setChecked(isDarkMode);
        darkMode.setSummary(isDarkMode ? "On" : "Off");

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

        haptics = findPreference("haptics");
        haptics.setChecked(preferences.getBoolean("haptics", true));
        haptics.setOnPreferenceClickListener(preference1 -> {
            if (haptics.isChecked()) {
                haptics.setSummary("Enabled");
                preferences.edit().putBoolean("haptics", true).apply();
            } else {
                haptics.setSummary("Disabled");
                preferences.edit().putBoolean("haptics", false).apply();
            }
            return false;
        });

        forgot = findPreference("forgot");
        forgot.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(requireContext(), ForgotPasswordActivity.class));
            return false;
        });
    }
}