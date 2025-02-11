package com.example.startinglinefitnesstracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProgressActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private WorkoutDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
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
                    return true;  // Already on Progress screen

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


        // Initialize the TableLayout and Database Helper
        tableLayout = findViewById(R.id.table_layout);
        dbHelper = new WorkoutDatabaseHelper(this);

        // Display workouts in the table
        displayWorkouts();

    }

    private void displayWorkouts() {
        Cursor cursor = dbHelper.getAllWorkouts();

        if (cursor != null && cursor.getCount() > 0) {
            int rowIndex = 0; // Keep track of row index
            while (cursor.moveToNext()) {
                // Retrieve data
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String workoutType = cursor.getString(cursor.getColumnIndex("workout_type"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                double distance = cursor.getDouble(cursor.getColumnIndex("distance"));

                // Create a new row
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(10, 10, 10, 10);

                // Alternate colors based on index
                if (rowIndex % 2 == 0) {
                    tableRow.setBackgroundColor(Color.parseColor("#B2DFDB")); // Teal lighter
                } else {
                    tableRow.setBackgroundColor(Color.parseColor("#E0F2F1")); // Teal even lighter
                }

                // Create TextViews for each column
                TextView dateTextView = new TextView(this);
                dateTextView.setText(date);
                dateTextView.setPadding(5, 5, 5, 5);
                dateTextView.setGravity(Gravity.CENTER);
                tableRow.addView(dateTextView);

                TextView workoutTypeTextView = new TextView(this);
                workoutTypeTextView.setText(workoutType);
                workoutTypeTextView.setPadding(5, 5, 5, 5);
                workoutTypeTextView.setGravity(Gravity.CENTER);
                tableRow.addView(workoutTypeTextView);

                TextView timeTextView = new TextView(this);
                timeTextView.setText(time);
                timeTextView.setPadding(5, 5, 5, 5);
                timeTextView.setGravity(Gravity.CENTER);
                tableRow.addView(timeTextView);

                TextView distanceTextView = new TextView(this);
                distanceTextView.setText(String.format("%.2f km", distance));
                distanceTextView.setPadding(5, 5, 5, 5);
                distanceTextView.setGravity(Gravity.CENTER);
                tableRow.addView(distanceTextView);

                // Add the row to the table layout
                tableLayout.addView(tableRow);

                rowIndex++; // Increment row index
            }
        } else {
            Log.d("ProgressActivity", "No workouts found in database.");
        }
        cursor.close();
    }
}