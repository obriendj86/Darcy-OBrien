package com.example.startinglinefitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;

public class StartWorkoutActivity extends AppCompatActivity {

    private TextView timerDisplay;
    private Button startButton, stopButton, pauseButton;
    private RadioGroup radioGroupWorkoutType;
    private EditText distanceInput;
    private Timer timer;
    private boolean isRunning = false;
    private long startTime = 0, elapsedTime = 0;
    private Handler handler = new Handler();
    private boolean isPaused = false;
    private String workoutType;
    private WorkoutDatabaseHelper dbHelper; // Assume this is a helper class for database operations.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
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
                    startActivity(new Intent(this, TipsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_start:
                    return true;

                default:
                    return false;
            }
        });

        // Initialize views
        timerDisplay = findViewById(R.id.timer_display);
        radioGroupWorkoutType = findViewById(R.id.radioGroupWorkoutType);
        distanceInput = findViewById(R.id.distance_input);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        pauseButton = findViewById(R.id.pause_button);
        dbHelper = new WorkoutDatabaseHelper(this); // Initialize the database helper

        // Setup radio buttons listener
        radioGroupWorkoutType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_run) {
                workoutType = "Run";
            } else if (checkedId == R.id.radio_walk) {
                workoutType = "Walk";
            } else if (checkedId == R.id.radio_cross_training) {
                workoutType = "Cross-training";
            }
        });

        // Start button
        startButton.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime() - elapsedTime;
                handler.postDelayed(updateTimerRunnable, 0);
                isRunning = true;
                isPaused = false;
            }
        });

        // Pause button
        pauseButton.setOnClickListener(v -> {
            if (isRunning) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime;
                handler.removeCallbacks(updateTimerRunnable);
                isRunning = false;
                isPaused = true;
            }
        });

        // Stop button
        stopButton.setOnClickListener(v -> {
            handler.removeCallbacks(updateTimerRunnable);
            elapsedTime = 0;
            isRunning = false;
            isPaused = false;
            timerDisplay.setText("00:00:00");
        });

        // Save button
        findViewById(R.id.save_button).setOnClickListener(v -> saveWorkout());

        // Discard button
        findViewById(R.id.discard_button).setOnClickListener(v -> resetWorkout());
    }

    private void saveWorkout() {
        // Get the distance from the input and convert to miles
        String distanceText = distanceInput.getText().toString();
        if (distanceText.isEmpty()) {
            distanceText = "0"; // default to 0 if no input
        }
        double distance = Double.parseDouble(distanceText);

        // Format time as "HH:mm:ss"
        long elapsedMillis = SystemClock.elapsedRealtime() - startTime;
        int seconds = (int) (elapsedMillis / 1000) % 60;
        int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
        int hours = (int) ((elapsedMillis / (1000 * 60 * 60)) % 24);
        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        // Get user ID (this could be retrieved from a shared preference or session)
        int userId = getUserId(); // Replace this with actual user ID retrieval logic

        // Save workout to the database
        boolean isSaved = dbHelper.saveWorkout(userId, workoutType, formattedTime, distance);

        // Show a toast based on success
        String toastMessage = isSaved ? "Workout saved successfully!" : "Failed to save workout!";
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        // Navigate back to the main dashboard
        if (isSaved) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }
    }

    private void resetWorkout() {
        // Reset all fields
        radioGroupWorkoutType.clearCheck();
        distanceInput.setText("");
        timerDisplay.setText("00:00:00");
        elapsedTime = 0;
        isRunning = false;
        isPaused = false;
    }

    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long elapsedMillis = SystemClock.elapsedRealtime() - startTime;
            int seconds = (int) (elapsedMillis / 1000) % 60;
            int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
            int hours = (int) ((elapsedMillis / (1000 * 60 * 60)) % 24);
            timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };

    private int getUserId() {
        // Fetch the user ID from shared preferences, session, or intent
        return 1; // Replace with actual logic to get the logged-in user's ID
    }
}