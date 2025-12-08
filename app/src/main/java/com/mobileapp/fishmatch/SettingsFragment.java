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

        // display point total
        updatePointTotal(prefs);

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

        /** disable radio buttons for locked items **/
        // sea blue
        if (prefs.getBoolean("tile_sea_blue_purchased", true)) {
            // unlocked
            binding.tileSeaBluePurchaseButton.setEnabled(false);
            binding.tileSeaBlueButton.setEnabled(true);
        } else {
            // locked
            binding.tileSeaBluePurchaseButton.setEnabled(true);
            binding.tileSeaBlueButton.setEnabled(false);
        }
        // fire coral
        if (prefs.getBoolean("tile_fire_coral_purchased", false)) {
            // unlocked
            binding.tileFireCoralPurchaseButton.setEnabled(false);
            binding.tileFireCoralButton.setEnabled(true);
        } else {
            // locked
            binding.tileFireCoralPurchaseButton.setEnabled(true);
            binding.tileFireCoralButton.setEnabled(false);
        }
        // seaweed
        if (prefs.getBoolean("tile_seaweed_purchased", false)) {
            // unlocked
            binding.tileSeaweedPurchaseButton.setEnabled(false);
            binding.tileSeaweedButton.setEnabled(true);
        } else {
            // locked
            binding.tileSeaweedPurchaseButton.setEnabled(true);
            binding.tileSeaweedButton.setEnabled(false);
        }
        // ocean depth
        if (prefs.getBoolean("tile_ocean_depth_purchased", false)) {
            // unlocked
            binding.tileOceanDepthPurchaseButton.setEnabled(false);
            binding.tileOceanDepthButton.setEnabled(true);
        } else {
            // locked
            binding.tileOceanDepthPurchaseButton.setEnabled(true);
            binding.tileOceanDepthButton.setEnabled(false);
        }
        // snail shell
        if (prefs.getBoolean("tile_snail_shell_purchased", false)) {
            // unlocked
            binding.tileSnailShellPurchaseButton.setEnabled(false);
            binding.tileSnailShellButton.setEnabled(true);
        } else {
            // locked
            binding.tileSnailShellPurchaseButton.setEnabled(true);
            binding.tileSnailShellButton.setEnabled(false);
        }
        // starfish
        if (prefs.getBoolean("tile_starfish_purchased", false)) {
            // unlocked
            binding.tileStarfishPurchaseButton.setEnabled(false);
            binding.tileStarfishButton.setEnabled(true);
        } else {
            // locked
            binding.tileStarfishPurchaseButton.setEnabled(true);
            binding.tileStarfishButton.setEnabled(false);
        }
        // crown
        if (prefs.getBoolean("tile_crown_purchased", false)) {
            // unlocked
            binding.tileCrownPurchaseButton.setEnabled(false);
            binding.tileCrownButton.setEnabled(true);
        } else {
            // locked
            binding.tileCrownPurchaseButton.setEnabled(true);
            binding.tileCrownButton.setEnabled(false);
        }
        // sunset
        if (prefs.getBoolean("tile_sunset_purchased", false)) {
            // unlocked
            binding.tileSunsetPurchaseButton.setEnabled(false);
            binding.tileSunsetButton.setEnabled(true);
        } else {
            // locked
            binding.tileSunsetPurchaseButton.setEnabled(true);
            binding.tileSunsetButton.setEnabled(false);
        }
        // fishy tile
        if (prefs.getBoolean("tile_fishy_tile_purchased", false)) {
            // unlocked
            binding.tileFishyTilePurchaseButton.setEnabled(false);
            binding.tileFishyTileButton.setEnabled(true);
        } else {
            // locked
            binding.tileFishyTilePurchaseButton.setEnabled(true);
            binding.tileFishyTileButton.setEnabled(false);
        }
        /** set callbacks for shop items **/
        // sea blue
        binding.tileSeaBluePurchaseButton.setOnClickListener(v -> {
            // no behavior, button should always be disabled since this skin is free
        });
        // fire coral
        binding.tileFireCoralPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 25;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_fire_coral_purchased", true);
                editor.apply();
                binding.tileFireCoralPurchaseButton.setEnabled(false);
                binding.tileFireCoralButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // seaweed
        binding.tileSeaweedPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 25;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_seaweed_purchased", true);
                editor.apply();
                binding.tileSeaweedPurchaseButton.setEnabled(false);
                binding.tileSeaweedButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // ocean depth
        binding.tileOceanDepthPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 50;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_ocean_depth_purchased", true);
                editor.apply();
                binding.tileOceanDepthPurchaseButton.setEnabled(false);
                binding.tileOceanDepthButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // snail shell
        binding.tileSnailShellPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 100;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_snail_shell_purchased", true);
                editor.apply();
                binding.tileSnailShellPurchaseButton.setEnabled(false);
                binding.tileSnailShellButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // starfish
        binding.tileStarfishPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 200;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_starfish_purchased", true);
                editor.apply();
                binding.tileStarfishPurchaseButton.setEnabled(false);
                binding.tileStarfishButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // crown
        binding.tileCrownPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 200;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_crown_purchased", true);
                editor.apply();
                binding.tileCrownPurchaseButton.setEnabled(false);
                binding.tileCrownButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // sunset
        binding.tileSunsetPurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 300;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_sunset_purchased", true);
                editor.apply();
                binding.tileSunsetPurchaseButton.setEnabled(false);
                binding.tileSunsetButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });
        // fishy tile
        binding.tileFishyTilePurchaseButton.setOnClickListener(v -> {
            int pointTotal = prefs.getInt("point_total", 0);
            int price = 300;
            if (pointTotal >= price) {
                pointTotal -= price;
                editor.putInt("point_total", pointTotal);
                editor.putBoolean("tile_fishy_tile_purchased", true);
                editor.apply();
                binding.tileFishyTilePurchaseButton.setEnabled(false);
                binding.tileFishyTileButton.setEnabled(true);
                updatePointTotal(prefs);
            }
        });

        /** set tile back to selected image on click **/
        binding.tileBackButtons.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.tileSeaBlueButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_sea_blue);
            } else if (checkedId == binding.tileFireCoralButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_fire_coral);
            } else if (checkedId == binding.tileSeaweedButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_seaweed);
            } else if (checkedId == binding.tileOceanDepthButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_ocean_depth);
            } else if (checkedId == binding.tileSnailShellButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_snail_shell);
            } else if (checkedId == binding.tileStarfishButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_starfish);
            } else if (checkedId == binding.tileCrownButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_crown);
            } else if (checkedId == binding.tileSunsetButton.getId()) {
                editor.putInt("tile_back", R.drawable.tile_sunset);
            } else {
                editor.putInt("tile_back", R.drawable.tile_fishy_tile);
            }
            editor.apply();
        });

        return view;
    }

    void updatePointTotal(SharedPreferences prefs) {
        binding.pointTotalText.setText("Points Available: " + prefs.getInt("point_total", 0));
    }
}