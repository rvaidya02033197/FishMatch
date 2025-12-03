package com.mobileapp.fishmatch;

import android.util.Log;
import android.view.View;

import java.util.Random;

public class FishMatch {
    // internal random number generator
    private final Random rng = new Random();
    // store fish names for convenience and intuitive comparison
    private String fish1 = "";
    private String fish2 = "";
    // store tiles that are currently face-up
    private View tile1 = null;
    private View tile2 = null;
    // scoreToWin = total number of tiles, should be assigned by GameFragment in initButtonVector()
    private int scoreToWin = -1;
    private int userScore = 0;
    // constant tile background todo: allow user to customize this image
    private final int tileBack = R.drawable.stripes;
    // constant millisecond delay before taking action after 2nd tile flip
    private final int msDelay = 1000;
    // boolean flag indicating if two tiles have been flipped, used in callback to prevent 3+ flips at a time
    // similar to a mutex lock. The "lock" is released in clear()
    public boolean canFlip = true;
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
            tile1 = tile;
        } else if (fish2.isEmpty() && tile1 != tile) {
            // set second fish
            fish2 = name;
            tile2 = tile;
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

    /** set the score to win after determining number of tiles in play **/
    public void setScoreToWin(int x) {
        scoreToWin = x;
    }

    // private methods
    /** once two fish are revealed, performs comparison and determines next step **/
    private void compare() {
        // canFlip functions similar to a mutex lock, prevents any more than two tiles from being flipped
        // during the delay in tile2.postDelayed()
        canFlip = false;
        Log.d("Gameplay", "Comparing " + fish1 + " and " + fish2);
        if (fish1.equals(fish2)) {
            // match the two tiles
            tile2.postDelayed(() -> match(), msDelay);
        } else {
            // reset tiles
            tile2.postDelayed(() -> clear(false), msDelay);   // match() will also call clear(), but with 'true' argument
        }
    }

    /** increase player's score and remove the tiles from play, then clear references **/
    private void match() {
        // increase score
        userScore += 2;
        // disable and hide tiles
        tile1.setEnabled(false);
        tile2.setEnabled(false);
        tile1.setAlpha(0);
        tile2.setAlpha(0);
        // remove references to tiles
        clear(true);
        // check for a win
        if (userScore >= scoreToWin) {
            Log.d("Gameplay", "You win!");
        } else {
            Log.d("Gameplay", "Player now has " + userScore + " points");
        }
    }

    /** clear references to previous tiles, reset fish names **/
    private void clear(boolean success) {
        // reset fish
        fish1 = "";
        fish2 = "";
        if (!success) {
            // flip tiles face-down
            tile1.setBackgroundResource(tileBack);
            tile2.setBackgroundResource(tileBack);
        }
        // reset tile references
        tile1 = null;
        tile2 = null;
        // release "lock" and allow for flipping
        canFlip = true;
    }
}
