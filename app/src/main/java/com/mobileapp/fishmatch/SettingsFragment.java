package com.mobileapp.fishmatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileapp.fishmatch.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    // view binding
    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // get shared preference
        SharedPreferences prefs = requireActivity().getSharedPreferences("FishMatchSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // display current preferred speed
        int gameSpeed = prefs.getInt("game_speed", 1);
        if (gameSpeed == 1) {
            binding.speed1x.setChecked(true);
        } else if (gameSpeed == 2) {
            binding.speed2x.setChecked(true);
        } else {
            binding.speed4x.setChecked(true);
        }

        // callback for radio group
        binding.gameSpeedGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.speed1x.getId()) {
                editor.putInt("game_speed", 1);
            } else if (checkedId == binding.speed2x.getId()) {
                editor.putInt("game_speed", 2);
            } else {
                editor.putInt("game_speed", 4);
            }
            editor.apply();
        });

        return view;
    }
}