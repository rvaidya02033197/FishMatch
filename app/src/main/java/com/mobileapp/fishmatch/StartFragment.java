package com.mobileapp.fishmatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobileapp.fishmatch.databinding.FragmentGameBinding;
import com.mobileapp.fishmatch.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {
    // view binding
    private FragmentStartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // set the difficulty "easy" to be selected by default initially
        binding.easyDifficulty.setChecked(true);

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