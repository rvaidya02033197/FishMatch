package com.mobileapp.fishmatch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Stats stats = StatsController.load(requireContext());

        // reset stats button callback
        binding.resetStatsButton.setOnClickListener(new View.OnClickListener() {
            // protect the user from resetting stats on the first click
            boolean safe = true;

            @Override
            public void onClick(View v) {
                if (safe) {
                    binding.resetStatsButton.setText(R.string.reset_stats_confirm);
                    safe = false;
                } else {
                    // reset stats
                    StatsController.clear(requireContext());
                    binding.resetStatsButton.setText("Please Refresh This Page");
                    loadStats(stats);
                }
            }
        });

        // share stats button callback
        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // implementation from: https://www.youtube.com/watch?v=qbtlrGHOVjg
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, StatsController.export(stats));
                intent.setType("text/plain");

                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // update the text fields
        loadStats(stats);

        return view;
    }

    private void loadStats(Stats stats)  {
        // retrieve stats
        DifficultyStats easy = stats.easy;
        DifficultyStats medium = stats.medium;
        DifficultyStats hard = stats.hard;
        DifficultyStats total = StatsController.total(stats);

        // set games played
        binding.easyGamesPlayedText.setText("Games Played: " + easy.gamesPlayed);
        binding.mediumGamesPlayedText.setText("Games Played: " + medium.gamesPlayed);
        binding.hardGamesPlayedText.setText("Games Played: " + hard.gamesPlayed);
        binding.totalGamesPlayedText.setText("Games Played: " + total.gamesPlayed);

        // Fastest times
        setTime(binding.easyFastestTimeText, easy.fastestTime);
        setTime(binding.mediumFastestTimeText, medium.fastestTime);
        setTime(binding.hardFastestTimeText, hard.fastestTime);
        setTime(binding.totalFastestTimeText, total.fastestTime);

        // Points scored
        binding.easyPointsScoredText.setText("Points Scored: " + easy.pointsScored);
        binding.mediumPointsScoredText.setText("Points Scored: " + medium.pointsScored);
        binding.hardPointsScoredText.setText("Points Scored: " + hard.pointsScored);
        binding.totalPointsScoredText.setText("Points Scored: " + total.pointsScored);

        // Least flips
        setFlips(binding.easyLeastFlipsText, easy.leastFlips);
        setFlips(binding.mediumLeastFlipsText, medium.leastFlips);
        setFlips(binding.hardLeastFlipsText, hard.leastFlips);
        setFlips(binding.totalLeastFlipsText, total.leastFlips);
    }
    private void setTime(TextView tv, long time) {
        if (time == Long.MAX_VALUE) {
            tv.setText("Fastest Time: ---");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
            tv.setText("Fastest Time: " + seconds + "s");
        }
    }

    private void setFlips(TextView tv, int flips) {
        if (flips == Integer.MAX_VALUE) {
            tv.setText("Shortest Win: ---");
        } else {
            tv.setText("Shortest Win: " + flips + " Turns");
        }
    }
}