package com.mobileapp.fishmatch;

import android.util.Log;
import android.view.View;

import com.mobileapp.fishmatch.databinding.FragmentGameBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishMatch {
    /*************************************
     *   PRIVATE MEMBER VARIABLES
     ************************************/

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

    // internal speed modifier
    private int speed = 1;

    // millisecond delay before taking action after 2nd tile flip
    private int msDelay = 1000;
    // millisecond delay for fade-out time
    private int msFadeOut = 400;

    private int msFlipAnimation = 100;

    private final List<String> matchCongrats = Arrays.asList(
            "Nice!",
            "Impressive!",
            "Great Match!",
            "Fishtacular!",
            "Cod Damn!",
            "Fintastic!",
            "Wowza!",
            "Holy Mackerel!",
            "Fish!",
            "Great Catch!",
            "Holy Fishpaste!",
            "Catch of the Day!"
    );

    private FragmentGameBinding gameBinding;

    private GameMessageListener messageListener;

    private WinListener winListener;

    /*************************************
     *   PUBLIC MEMBER VARIABLES
     ************************************/

    /* boolean flag indicating if two tiles have been flipped, used in callback to prevent
    3+ flips at a time similar to a mutex lock. The "lock" is released in clear()   */
    public boolean canFlip = true;

    // -1 undefined, 1 easy, 2 medium, 3 hard
    public int difficulty = -1;

    // number of flips made
    public int flips = 0;

    // constant tile background
    public int tileBack = R.drawable.tile_sea_blue;

    /*************************************
     *   PUBLIC METHODS
     ************************************/

    public interface GameMessageListener {
        void onMessage(String text);
    }

    public void setMessageListener(GameMessageListener listener) {
        this.messageListener = listener;
    }

    public interface WinListener {
        void onWin();
    }

    public void setWinListener(WinListener listener) {
        this.winListener = listener;
    }

    // When win condition is detected
    private void checkWin() {
        if (userScore >= scoreToWin) {
            winListener.onWin();
        }
    }

    /** set the in game speed **/
    public void setSpeed(int speed) {
        this.speed = speed;
        this.msDelay = 1000 / speed;
        this.msFadeOut = 400 / speed;
        Log.d("Game Setup", "Game speed set to " + speed + "x");
        Log.d("Game Setup", "msDelay: " + msDelay + ", msFadeOut: " + msFadeOut);
    }

    /** notify the game handler that a tile has been flipped, provide the tile and fish image **/
    public void flip(View tile, Integer fishImage) {
        // determine name from image
        String name = tile.getResources().getResourceEntryName(fishImage);
        Log.d("Gameplay", "Flipped a tile with the image: " + name);

        // Animate turn over for flip effect
        tile.animate().rotationY(90).setDuration(100 / this.speed).withEndAction(() -> {

            tile.setBackgroundResource(fishImage);

            tile.setRotationY(-90);
            tile.animate().rotationY(0).setDuration(300 / this.speed);
        });

        // has a tile been flipped already?
        if (fish1.isEmpty()) {
            fish1 = name;
            tile1 = tile;
            // register the flip
            this.flips++;
            Log.d("Gameplay", "Flips: " + this.flips);
        } else if (fish2.isEmpty() && tile1 != tile) {  // is string empty and not the same tile as first flip?
            // set second fish
            fish2 = name;
            tile2 = tile;
            // register the flip
            this.flips++;
            compare();
        } else if (tile1 == tile) {
            // ignore
        } else {
            // you shouldn't end up here, something is very wrong
            Log.d("Error", "Game handler has " + fish1 + " and " + fish2 + ", undefined behavior");
        }
    }

    /** simple helper function for main driver, provides easy random indexing **/
    public int randomIndex(int size) { return rng.nextInt(size); }

    /** set the score to win after determining number of tiles in play **/
    public void setScoreToWin(int x) { scoreToWin = x; }

    /** return the number of points the player has earned **/
    public int userPoints() { return this.userScore; }

    /*************************************
     *   PRIVATE METHODS
     ************************************/

    /** once two fish are revealed, performs comparison and determines next step **/
    private void compare() {
        /* canFlip functions similar to a mutex lock, prevents any more than
        two tiles from being flipped during the delay in tile2.postDelayed() */
        canFlip = false;
        Log.d("Gameplay", "Comparing " + fish1 + " and " + fish2);

        if (fish1.equals(fish2)) {
            // match the two tiles, force a delay before proceeding to allow user to see both tiles
            tile2.postDelayed(() -> match(), msDelay);
        } else {
            // reset tiles, force a delay to allow user to see both tiles
            tile2.postDelayed(() -> clear(false), msDelay);   // match() will also call clear(), but with 'true' argument
        }
    }

    /** increase player's score and remove the tiles from play, then clear references **/
    private void match() {
        // increase score
        userScore += 2;

        // disable tiles
        tile1.setEnabled(false);
        tile2.setEnabled(false);

        // scale up (grow) and fade out
        tile1.animate().scaleX(1.2f).scaleY(1.2f).alpha(0).setDuration(msFadeOut);
        tile2.animate().scaleX(1.2f).scaleY(1.2f).alpha(0).setDuration(msFadeOut);

        // remove internal references to tiles
        clear(true);

        // check for a win
        if (userScore >= scoreToWin) {
            checkWin();
            Log.d("Gameplay", "You win!");
        } else {
            // Listener message passing to display congrats on correct matchings
            if (messageListener != null) {
                messageListener.onMessage(matchCongrats.get(this.randomIndex(matchCongrats.size())));
            }
            Log.d("Gameplay", "Player now has " + userScore + " points");
        }
    }

    /** clear references to previous tiles, reset fish names **/
    private void clear(boolean success) {
        // reset fish names
        fish1 = "";
        fish2 = "";

        // if not successful match, flip tiles face-down with animation
        if (!success && tile1 != null && tile2 != null) {
            animateFlipBack(tile1);
            animateFlipBack(tile2);
        }

        // reset internal tile references
        tile1 = null;
        tile2 = null;

        // release flipping "lock"
        canFlip = true;
    }
    private void animateFlipBack(View tile) {
        tile.animate().rotationY(-90).setDuration(150).withEndAction(() -> {
            tile.setBackgroundResource(tileBack);
            tile.setRotationY(90);
            tile.animate().rotationY(0).setDuration(150).start();
        }).start();
    }

}
