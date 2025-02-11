package com.example.startinglinefitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout_db";
    private static final int DATABASE_VERSION = 2;  // Incremented version

    public WorkoutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with the 'date' column
        String createTableQuery = "CREATE TABLE workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "workout_type TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "distance DOUBLE NOT NULL," +
                "date TEXT NOT NULL, " +  // Added the 'date' column here
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If upgrading the database, drop the old table and recreate it
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS workout");
            onCreate(db);
        }
    }

    // Save workout data, including the date
    public boolean saveWorkout(int userId, String workoutType, String time, double distance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("workout_type", workoutType);
        contentValues.put("time", time);
        contentValues.put("distance", distance);

        // Get the current date in MM/dd/yyyy format
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        contentValues.put("date", currentDate);

        long result = db.insert("workout", null, contentValues);
        db.close();
        return result != -1;
    }

    // Method to retrieve all workouts
    public Cursor getAllWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Return a Cursor with all the workouts ordered by date (descending)
        return db.query("workout", null, null, null, null, null, "id DESC");
    }
}