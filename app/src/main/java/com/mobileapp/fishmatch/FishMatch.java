package com.mobileapp.fishmatch;

import android.util.Log;
import android.view.View;

import java.util.Random;

public class FishMatch {
    private Random rng = new Random();
    private String fish1 = "";
    private String fish2 = "";

    // public methods
    /** notify the game handler that a tile has been flipped, provide the tile and fish image **/
    public void flip(View tile, Integer fishImage) {
        // determine name from image
        String name = tile.getResources().getResourceEntryName(fishImage);
        Log.d("Gameplay", "Flipped a tile with the image: " + name);

        // has a tile been flipped already?
        if (fish1.isEmpty()) {
            // set first fish
            fish1 = name;
        } else if (fish2.isEmpty()) {
            // set second fish
            fish2 = name;
            compare();
        } else {
            // you shouldn't end up here, start freaking out
            Log.d("Error", "Game handler has " + fish1 + " and " + fish2 + ", undefined behavior");
        }
    }

    /** simple helper function for main driver, provides easy random indexing **/
    public int randomIndex(int size) {
        return rng.nextInt(size);
    }

    // private methods
    /** once two fish are revealed, performs comparison and determines next step **/
    private void compare() {
        Log.d("Gameplay", "Comparing " + fish1 + " and " + fish2);

    }
}
