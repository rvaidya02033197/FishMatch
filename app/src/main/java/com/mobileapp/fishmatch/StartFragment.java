package com.mobileapp.fishmatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileapp.fishmatch.databinding.FragmentStartBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StartFragment extends Fragment {
    // view binding
    private FragmentStartBinding binding;

    private final List<String> startQuips = Arrays.asList(
            "Let's Match Some Fish, Shall We?",
            "Are You as Excited to Match as I am?\nLet's Get Started",
            "Are you Ready Captain?",
            "What are You Waiting For?\nLet's get Matching!",
            "If You Got a Nickel For Every Fish Matched...\n You Could be Very Rich One Day",
            "I don't Know About You,\n But These Fish Are in For a Good Matching",
            "Why Don't You Show Me What You're Made Of",
            "The Fish Are Ready,\nAre You?",
            "Match Me to the Moon,\nAnd Let Me Fish Among the Stars",
            "I Think We've Been Waiting Long Enough,\nIt's Matching Time",
            "About Time You Showed Up, Let's Match Some Fish",
            "We've Been Waiting For You, It's Time to Match",
            "Let's Dive In!"
            );

    private final Random rng = new Random();
    public int randomIndex(int size) { return rng.nextInt(size); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // set the difficulty "easy" to be selected by default initially
        binding.easyDifficulty.setChecked(true);

        // Set a random start quip to screen
        binding.startScreenQuip.setText(startQuips.get(this.randomIndex(startQuips.size())));

        // set the play button callback function
        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // determine difficulty
                int difficulty = -1;
                if (binding.easyDifficulty.isChecked()) {
                    difficulty = 1;
                } else if (binding.mediumDifficulty.isChecked()) {
                    difficulty = 2;
                } else if (binding.hardDifficulty.isChecked()) {
                    difficulty = 3;
                }
                StartFragmentDirections.ActionStartFragmentToGameFragment action = StartFragmentDirections.actionStartFragmentToGameFragment().setDifficulty(difficulty);

                Navigation.findNavController(v).navigate(action);
            }
        });
        return view;

    }
}