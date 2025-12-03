package com.mobileapp.fishmatch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.mobileapp.fishmatch.databinding.FragmentGameBinding;

import java.util.Arrays;
import java.util.Vector;

public class GameFragment extends Fragment {
    // view binding
    private FragmentGameBinding binding;
    // button vector
    private Vector<ImageButton> tiles = new Vector<>();
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
    private FishMatch game = new FishMatch();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize view binding, inflate layout
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // add ImageButtons to array for easier referencing, tiles[i] = tile_i
        initButtonVector();
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
    private void initButtonVector() {
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
        game.setScoreToWin(tiles.size());
        Log.d("Game Setup", "Set scoreToWin to " + tiles.size());
    }

    /** set the onclick listener after determining what fish is associated with this tile **/
    private void setTileBehavior(ImageButton tile, Integer fishImage) {
        tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if you can flip, then flip
                if (game.canFlip) {
                    view.setBackgroundResource(fishImage);
                    game.flip(view, fishImage);
                }
            }
        });
    }
}