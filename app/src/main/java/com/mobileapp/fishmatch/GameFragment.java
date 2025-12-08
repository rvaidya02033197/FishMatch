package com.mobileapp.fishmatch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.mobileapp.fishmatch.databinding.FragmentGameBinding;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class GameFragment extends Fragment {
    // view binding
    private FragmentGameBinding binding;

    // button vector
    private Vector<ImageButton> tiles = new Vector<>();

    private boolean clockRunning = false;

    private long baseTime = 0;

    // const fish image array
    private Vector<Integer> fishImages = new Vector<>(Arrays.asList(
            R.drawable.bass,
            R.drawable.butterfish,
            R.drawable.cod,
            R.drawable.coelacanth,
            R.drawable.flounder,
            R.drawable.grouper,
            R.drawable.hake,
            R.drawable.herring,
            R.drawable.marlin,
            R.drawable.rockfish,
            R.drawable.salmon,
            R.drawable.scup,
            R.drawable.snapper,
            R.drawable.sturgeon,
            R.drawable.trout,
            R.drawable.tuna
    ));

    // game manager
    private FishMatch game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        int difficulty = GameFragmentArgs.fromBundle(getArguments()).getDifficulty();

        game = new FishMatch();

        // This is a listener to keep UI out of FishMatch Logic File
        game.setMessageListener(message -> {
            binding.inGameMessages.setAlpha(1f);

            binding.inGameMessages.setText(message);

            // Congrats text fade away effect.
            binding.inGameMessages.animate().alpha(0f).setDuration(1000);
        });

        // Win listener so game fragment knows when wins happen immediately
        game.setWinListener(() -> {
            gameEnd();
        });

        // add ImageButtons to array for easier referencing, tiles[i] = tile_i
        initButtonVector(difficulty);

        // determine fish placement and add listeners
        initFish(tiles.size());

        return view;
    }

    /** choose two tiles at a time and give them the same fish image in their callback function **/
    private void initFish(int tileCount) {
        Log.d("Game Setup", "Working with " + tileCount + " tiles and " + fishImages.size() + " fish images");
        Log.d("Game Setup", "Need to make " + tileCount/2 + " pairs of fish");

        // make shallow copy of vector which allows us to pop tiles that have been assigned fish
        Vector<ImageButton> tilesCopy = new Vector<>(tiles);

        // make shallow copy of fish image
        Vector<Integer> fishImagesCopy = new Vector<>(fishImages);

        // iterate until all tiles have been assigned fish
        while (!tilesCopy.isEmpty()) {
            // determine fish to make a pair of
            int fishIndex = game.randomIndex(fishImagesCopy.size());

            // assign to first tile
            int t1 = game.randomIndex(tilesCopy.size());
            setTileBehavior(tilesCopy.get(t1), fishImagesCopy.get(fishIndex));

            // pop tile
            tilesCopy.remove(t1);

            // assign to second tile
            int t2 = game.randomIndex(tilesCopy.size());
            setTileBehavior(tilesCopy.get(t2), fishImagesCopy.get(fishIndex));

            // pop tile
            tilesCopy.remove(t2);

            // pop fish
            fishImagesCopy.remove(fishIndex);
        }
        Log.d("Game Setup", "Fish pairs determined and assigned");
    }

    /** manually construct the vector **/
    private void initButtonVector(int difficulty) {
        // manually add all buttons to array
        // todo: use a loop if possible
        tiles.add(binding.tile0);
        tiles.add(binding.tile1);
        tiles.add(binding.tile2);
        tiles.add(binding.tile3);
        tiles.add(binding.tile4);
        tiles.add(binding.tile5);
        tiles.add(binding.tile6);
        tiles.add(binding.tile7);
        tiles.add(binding.tile8);
        tiles.add(binding.tile9);
        tiles.add(binding.tile10);
        tiles.add(binding.tile11);
        tiles.add(binding.tile12);
        tiles.add(binding.tile13);
        tiles.add(binding.tile14);
        tiles.add(binding.tile15);
        if (difficulty > 1) {
            // medium
            tiles.add(binding.tile16);
            tiles.add(binding.tile17);
            tiles.add(binding.tile18);
            tiles.add(binding.tile19);
            if (difficulty > 2)  {
                // hard
                tiles.add(binding.tile20);
                tiles.add(binding.tile21);
                tiles.add(binding.tile22);
                tiles.add(binding.tile23);
            } else {
                // difficulty = 2, hide extra buttons
                binding.tile20.setAlpha(0f);
                binding.tile21.setAlpha(0f);
                binding.tile22.setAlpha(0f);
                binding.tile23.setAlpha(0f);
                // disable extra buttons
                binding.tile20.setEnabled(false);
                binding.tile21.setEnabled(false);
                binding.tile22.setEnabled(false);
                binding.tile23.setEnabled(false);
            }
        } else {
            // difficulty = 1, hide extra buttons
            binding.tile16.setAlpha(0f);
            binding.tile17.setAlpha(0f);
            binding.tile18.setAlpha(0f);
            binding.tile19.setAlpha(0f);
            binding.tile20.setAlpha(0f);
            binding.tile21.setAlpha(0f);
            binding.tile22.setAlpha(0f);
            binding.tile23.setAlpha(0f);
            // disable extra buttons
            binding.tile16.setEnabled(false);
            binding.tile17.setEnabled(false);
            binding.tile18.setEnabled(false);
            binding.tile19.setEnabled(false);
            binding.tile20.setEnabled(false);
            binding.tile21.setEnabled(false);
            binding.tile22.setEnabled(false);
            binding.tile23.setEnabled(false);
        }
        Log.d("Game Setup", "Button array initialized");
        game.setScoreToWin(tiles.size());
        Log.d("Game Setup", "Set scoreToWin to " + tiles.size());
    }

    /** set the onclick listener after determining what fish is associated with this tile **/
    private void setTileBehavior(ImageButton tile, Integer fishImage) {
        tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if you can flip, then flip
                if(!clockRunning) {
                    baseTime = SystemClock.elapsedRealtime();
                    binding.gameTimer.setBase(baseTime);
                    binding.gameTimer.start();
                    clockRunning = true;
                }
                if (game.canFlip) {
                    view.setBackgroundResource(fishImage);
                    game.flip(view, fishImage);
                }
            }
        });
    }

    // Function currently just stops timer, later on will navigate to win screen
    private void gameEnd() { // shared preferences (stats recorded)
        long gameLength = SystemClock.elapsedRealtime() - binding.gameTimer.getBase();
        binding.gameTimer.stop();

        SharedPreferences prefs = requireActivity().getSharedPreferences("FishMatchStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int currentGamesPlayed = prefs.getInt("games_played", 0);
        editor.putInt("games_played", currentGamesPlayed + 1);

        long currentFastestTime = prefs.getLong("fastest_time", Long.MAX_VALUE);
        if (gameLength < currentFastestTime) {
            editor.putLong("fastest_time", gameLength);
        }

        editor.apply();

    }
}