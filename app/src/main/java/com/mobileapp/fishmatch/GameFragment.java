package com.mobileapp.fishmatch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.mobileapp.fishmatch.databinding.FragmentGameBinding;
import java.util.Vector;

public class GameFragment extends Fragment {
    // view binding
    private FragmentGameBinding binding;
    // button vector
    private Vector<ImageButton> tiles = new Vector<>();
    // const fish image array
    private int[] fishImages = {
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
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // add ImageButtons to array for easier referencing, tiles[i] = tile_i
        initButtonArray();
        // determine fish placement and add listeners
        initFish(tiles.size());

        return view;
    }

    private void initFish(int tileCount) {
        Log.d("Game Setup", "Working with " + tileCount + " tiles and " + fishImages.length + " fish images");
        Log.d("Game Setup", "Need to make " + tileCount/2 + " pairs of fish");
        // temp, make all tiles change to sturgeon todo: assign proper pairs
        for (int i = 0; i < tileCount; i++) {
            tiles.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackgroundResource(R.drawable.sturgeon);
                }
            });
        }
        Log.d("Game Setup", "Fish pairs determined and assigned");
    }

    private void initButtonArray() {
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
        Log.d("Game Setup", "Button array initialized");
    }
}