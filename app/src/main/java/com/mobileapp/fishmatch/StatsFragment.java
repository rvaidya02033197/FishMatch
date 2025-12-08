package com.mobileapp.fishmatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileapp.fishmatch.databinding.FragmentStatsBinding;

import java.util.concurrent.TimeUnit; // Import for time formatting

public class StatsFragment extends Fragment {
    // view binding
    private FragmentStatsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences prefs = requireActivity().getSharedPreferences("FishMatchStats", Context.MODE_PRIVATE);

        // retrieve stats
        int easyGamesPlayed = prefs.getInt("easy_games_played", 0);
        int mediumGamesPlayed = prefs.getInt("medium_games_played", 0);
        int hardGamesPlayed = prefs.getInt("hard_games_played", 0);
        long easyFastestTime = prefs.getLong("easy_fastest_time", Long.MAX_VALUE);
        long mediumFastestTime = prefs.getLong("medium_fastest_time", Long.MAX_VALUE);
        long hardFastestTime = prefs.getLong("hard_fastest_time", Long.MAX_VALUE);
        int easyPointsScored = prefs.getInt("easy_points_scored", 0);
        int mediumPointsScored = prefs.getInt("medium_points_scored", 0);
        int hardPointsScored = prefs.getInt("hard_points_scored", 0);

        // calculate totals across all modes
        // games played
        int totalGamesPlayed = easyGamesPlayed + mediumGamesPlayed + hardGamesPlayed;
        // fastest time
        long totalFastestTime;
        if (easyFastestTime < mediumFastestTime) {
            if (easyFastestTime < hardFastestTime) {
                totalFastestTime = easyFastestTime;
            } else {
                totalFastestTime = hardFastestTime;
            }
        } else {
            if (mediumFastestTime < hardFastestTime) {
                totalFastestTime = mediumFastestTime;
            } else {
                totalFastestTime = hardFastestTime;
            }
        }
        // total points
        int totalPointsScored = easyPointsScored + mediumPointsScored + hardPointsScored;

        // set games played
        binding.easyGamesPlayedText.setText("Games Played: " + easyGamesPlayed);
        binding.mediumGamesPlayedText.setText("Games Played: " + mediumGamesPlayed);
        binding.hardGamesPlayedText.setText("Games Played: " + hardGamesPlayed);
        binding.totalGamesPlayedText.setText("Games Played: " + totalGamesPlayed);

        // set fastest times
        // easy
        if (easyFastestTime == Long.MAX_VALUE) {
            binding.easyFastestTimeText.setText("Fastest Time: ---");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(easyFastestTime);
            binding.easyFastestTimeText.setText("Fastest Time: " + seconds + "s");
        }
        // medium
        if (mediumFastestTime == Long.MAX_VALUE) {
            binding.mediumFastestTimeText.setText("Fastest Time: ---");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(mediumFastestTime);
            binding.mediumFastestTimeText.setText("Fastest Time: " + seconds + "s");
        }
        // hard
        if (hardFastestTime == Long.MAX_VALUE) {
            binding.hardFastestTimeText.setText("Fastest Time: ---");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(hardFastestTime);
            binding.hardFastestTimeText.setText("Fastest Time: " + seconds + "s");
        }
        // total
        if (totalFastestTime == Long.MAX_VALUE) {
            binding.totalFastestTimeText.setText("Fastest Time: ---");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(totalFastestTime);
            binding.totalFastestTimeText.setText("Fastest Time: " + seconds + "s");
        }

        // set points scored
        binding.easyPointsScoredText.setText("Points Scored: " + easyPointsScored);
        binding.mediumPointsScoredText.setText("Points Scored: " + mediumPointsScored);
        binding.hardPointsScoredText.setText("Points Scored: " + hardPointsScored);
        binding.totalPointsScoredText.setText("Points Scored: " + totalPointsScored);

        return view;
    }
}