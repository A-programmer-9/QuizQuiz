package com.mvc.quizquiz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPrefs {
    public static final String HIGHEST_SCORE = "highest_score";
    public static final String SAVED_INDEX = "saved_index";
    public static final String CURRENT_SCORE = "current_score";
    private final SharedPreferences preferences;

    public SharedPrefs(@NonNull Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void setHighestScore(int score) {
        int lastSavedScore = preferences.getInt(HIGHEST_SCORE, 0);

        if (score > lastSavedScore) {
            preferences.edit().putInt(HIGHEST_SCORE, score).apply();
        }
    }

    public int getHighestScore() {
        return preferences.getInt(HIGHEST_SCORE, 0);
    }

    public void setSavedIndex(int currentIndex) {
        preferences.edit().putInt(SAVED_INDEX, currentIndex).apply();
    }

    public int getSavedIndex() {
        return preferences.getInt(SAVED_INDEX, 0);
    }

    public void setCurrentScore(int currentScore) {
        preferences.edit().putInt(CURRENT_SCORE, currentScore).apply();
    }

    public int getCurrentScore() {
        return preferences.getInt(CURRENT_SCORE, 0);
    }


}
