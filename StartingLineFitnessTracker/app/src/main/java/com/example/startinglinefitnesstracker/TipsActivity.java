package com.example.startinglinefitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_training:
                    // Navigates back to MainActivity as requested
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_progress:
                    startActivity(new Intent(this, ProgressActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_tips:
                    return true;

                case R.id.nav_start:
                    startActivity(new Intent(this, StartWorkoutActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                default:
                    return false;
            }
        });

    }
}