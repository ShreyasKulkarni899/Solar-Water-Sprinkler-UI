package com.nkocet.untitled;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    public static final int UPDATE_NAV_HEADER = 1;
    // Variable declarations
    AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    LinearLayout linearLayout;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView name;
    View view;
    SharedPreferences preferences;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Variable initialisations
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.navName);
        linearLayout = view.findViewById(R.id.headerLayout);

        // Database initialisation
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        database = new Database(getApplicationContext());

        // Setting up initial data
        name.setText(preferences.getString("name", "No name"));

        // Inflating the navigation drawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_time_control_fragment,
                R.id.nav_crop_guide_fragment,
                R.id.nav_help_fragment,
                R.id.nav_settings_fragment)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // On Click Listeners
        linearLayout.setOnClickListener(v -> startActivityForResult(new Intent(getApplicationContext(), ProfileActivity.class), UPDATE_NAV_HEADER));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            name.setText(preferences.getString("name", "No name"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.power_off_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.power_off) {
            for (Card card : database.getData()) database.toggleStatus(card);
            Toast.makeText(this, "Power off", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void logout(MenuItem item) {
        getApplicationContext().deleteDatabase("database.db");
        getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE).edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}