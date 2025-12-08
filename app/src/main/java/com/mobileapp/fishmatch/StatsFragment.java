package com.mobileapp.fishmatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit; // Import for time formatting

public class StatsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences("FishMatchStats", Context.MODE_PRIVATE);

        int gamesPlayed = prefs.getInt("games_played", 0);
        long fastestTime = prefs.getLong("fastest_time", Long.MAX_VALUE);

        TextView gamesPlayedText = view.findViewById(R.id.gamesPlayedText);
        TextView fastestTimeText = view.findViewById(R.id.fastestTimeText);

        gamesPlayedText.setText("Games Played: " + gamesPlayed);

        if (fastestTime == Long.MAX_VALUE) {
            fastestTimeText.setText("Fastest Time: --");
        } else {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(fastestTime);
            fastestTimeText.setText("Fastest Time: " + seconds + "s");
        }

        return view;
    }
}