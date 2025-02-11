package com.example.startinglinefitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrainingMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_mactivity);
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
                    startActivity(new Intent(this, StartWorkoutActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                default:
                    return false;
            }
        });

        // Find the container
        LinearLayout trainingPlanContainer = findViewById(R.id.trainingPlanContainer);

        // Marathon training plan data
        String[] weeks = {
                "Week 1", "Week 2", "Week 3", "Week 4", "Week 5",
                "Week 6", "Week 7", "Week 8", "Week 9", "Week 10",
                "Week 11", "Week 12", "Week 13", "Week 14", "Week 15", "Week 16"
        };

        String[] details = {
                "Run 3 miles, Rest, Cross-train, Run 4 miles, Rest, Run 5 miles, Rest",
                "Run 4 miles, Rest, Cross-train, Run 5 miles, Rest, Run 6 miles, Rest",
                "Run 5 miles, Rest, Cross-train, Run 6 miles, Rest, Run 7 miles, Rest",
                "Run 6 miles, Rest, Cross-train, Run 7 miles, Rest, Run 8 miles, Rest",
                "Run 7 miles, Rest, Cross-train, Run 8 miles, Rest, Run 9 miles, Rest",
                "Run 8 miles, Rest, Cross-train, Run 9 miles, Rest, Run 10 miles, Rest",
                "Run 9 miles, Rest, Cross-train, Run 10 miles, Rest, Run 12 miles, Rest",
                "Run 10 miles, Rest, Cross-train, Run 12 miles, Rest, Run 14 miles, Rest",
                "Run 12 miles, Rest, Cross-train, Run 13 miles, Rest, Run 16 miles, Rest",
                "Run 13 miles, Rest, Cross-train, Run 14 miles, Rest, Run 18 miles, Rest",
                "Run 14 miles, Rest, Cross-train, Run 16 miles, Rest, Run 20 miles, Rest",
                "Run 20 miles, Rest, Cross-train, Run 18 miles, Rest, Marathon Race Day!",
                "Rest and recover",
                "Taper and race"
        };

        // Add data to container with CardView
        for (int i = 0; i < weeks.length; i++) {
            // Create a CardView for each week
            CardView cardView = new CardView(this);
            cardView.setCardBackgroundColor(Color.WHITE);
            cardView.setContentPadding(16, 16, 16, 16);
            cardView.setRadius(8f);
            cardView.setCardElevation(4f);

            // Create a LinearLayout for each card content
            LinearLayout cardContent = new LinearLayout(this);
            cardContent.setOrientation(LinearLayout.VERTICAL);
            cardContent.setPadding(16, 16, 16, 16);

            // Week TextView
            TextView weekTextView = new TextView(this);
            weekTextView.setText(weeks[i]);
            weekTextView.setTextSize(18);
            weekTextView.setTextColor(getResources().getColor(android.R.color.black));
            weekTextView.setTypeface(null, Typeface.BOLD);

            // Details TextView
            TextView detailsTextView = new TextView(this);
            detailsTextView.setText(details[i]);
            detailsTextView.setTextSize(14);
            detailsTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));

            // Add textViews to the card
            cardContent.addView(weekTextView);
            cardContent.addView(detailsTextView);

            // Add the card to the container
            cardView.addView(cardContent);
            trainingPlanContainer.addView(cardView);
        }
    }
}