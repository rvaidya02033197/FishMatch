package com.mobileapp.fishmatch;

import android.content.Context;
import android.content.SharedPreferences;

public class StatsController {
    // const key to access the shared preferences object
    private static final String PREFS_NAME = "FishMatchStats";

    // loads all statistics from the shared preferences object and returns them in a Stats object
    public static Stats load(Context c) {
        // access shared preferences object using PREFS_NAME, which is "FishMatchStats"
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // begin constructing a Stats object to return
        Stats stats = new Stats();

        // load all difficulty stats into the Stats object at the same time
        loadDifficulty(prefs, "easy_", stats.easy);
        loadDifficulty(prefs, "medium_", stats.medium);
        loadDifficulty(prefs, "hard_", stats.hard);

        return stats;
    }

    // saves all statistics to the shared preferences object using a Stats object
    public static void save(Context c, Stats stats) {
        // access shared preferences object using PREFS_NAME, which is "FishMatchStats"
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // retrieve editor from prefs
        SharedPreferences.Editor editor = prefs.edit();

        saveDifficulty(editor, "easy_", stats.easy);
        saveDifficulty(editor, "medium_", stats.medium);
        saveDifficulty(editor, "hard_", stats.hard);

        editor.apply();
    }

    // return a DifficultyStats object containing the notable stats considering all difficulties
    public static DifficultyStats total(Stats stats) {
        DifficultyStats total = new DifficultyStats();

        total.gamesPlayed = stats.easy.gamesPlayed + stats.medium.gamesPlayed + stats.hard.gamesPlayed;
        total.fastestTime = Math.min(stats.easy.fastestTime, Math.min(stats.medium.fastestTime, stats.hard.fastestTime));
        total.pointsScored = stats.easy.pointsScored + stats.medium.pointsScored + stats.hard.pointsScored;
        total.leastFlips = Math.min(stats.easy.leastFlips, Math.min(stats.medium.leastFlips, stats.hard.leastFlips));

        return total;
    }

    public static void clear(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    // helper function to load all stats given a difficulty as a string
    private static void loadDifficulty(SharedPreferences prefs, String diffPrefix, DifficultyStats ds) {
        ds.gamesPlayed = prefs.getInt(diffPrefix + "games_played", 0);         // games played
        ds.fastestTime = prefs.getLong(diffPrefix + "fastest_time", Long.MAX_VALUE);    // fastest time
        ds.pointsScored = prefs.getInt(diffPrefix + "points_scored", 0);       // points scored
        ds.leastFlips = prefs.getInt(diffPrefix + "least_flips", Integer.MAX_VALUE);    // least flips
    }

    // helper function to save all stat changes
    private static void saveDifficulty(SharedPreferences.Editor editor, String diffPrefix, DifficultyStats ds) {
        editor.putInt(diffPrefix + "games_played", ds.gamesPlayed);
        editor.putLong(diffPrefix + "fastest_time", ds.fastestTime);
        editor.putInt(diffPrefix + "points_scored", ds.pointsScored);
        editor.putInt(diffPrefix + "least_flips", ds.leastFlips);
    }
}
