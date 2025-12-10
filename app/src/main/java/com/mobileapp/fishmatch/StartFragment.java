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
            "What are You Waiting For?\nLet's get Matching!"
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