package com.example.startinglinefitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Spinner trainingPlanSpinner;
    private LinearLayout viewProgressButton;
    private LinearLayout tipsButton;
    private LinearLayout startWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Spinner and Buttons
        trainingPlanSpinner = findViewById(R.id.trainingPlanSpinner);
        viewProgressButton = findViewById(R.id.btn_progress_text);
        tipsButton = findViewById(R.id.btn_tips_motivation_text);
        startWorkoutButton = findViewById(R.id.btn_start_workout_text);

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Already on Home
                    return true;

                case R.id.nav_training:
                    handleTrainingNavigation();
                    return true;

                case R.id.nav_progress:
                    startActivity(new Intent(this, ProgressActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_tips:
                    startActivity(new Intent(this, TipsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_start:
                    startActivity(new Intent(this, StartWorkoutActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                default:
                    return false;
            }
        });

        // Set listeners for additional navigation buttons
        setAdditionalNavigationListeners();
    }

    private void handleTrainingNavigation() {
        // Get the selected training plan from the spinner
        String selectedPlan = trainingPlanSpinner.getSelectedItem().toString();

        // Navigate to the respective Activity
        Intent intent;
        if (selectedPlan.equals("Make a selection")) {
            Toast.makeText(this, "Please select a training plan.", Toast.LENGTH_SHORT).show();
        } else if (selectedPlan.equals("5K")) {
            intent = new Intent(this, Training5kActivity.class);
            startActivity(intent);
        } else if (selectedPlan.equals("Half Marathon")) {
            intent = new Intent(this, TrainingHMActivity.class);
            startActivity(intent);
        } else if (selectedPlan.equals("Marathon")) {
            intent = new Intent(this, TrainingMActivity.class);
            startActivity(intent);
        }
    }

    private void setAdditionalNavigationListeners() {
        // Set listener for View Progress Button
        viewProgressButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            startActivity(intent);
        });

        // Set listener for Tips Button
        tipsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent);
        });

        // Set listener for Start Workout Button
        startWorkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartWorkoutActivity.class);
            startActivity(intent);
        });
    }
}