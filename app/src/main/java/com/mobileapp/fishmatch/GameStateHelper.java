package com.mobileapp.fishmatch;

import android.util.Pair;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

// Helper class to organize game state information for clean state restoring
public class GameStateHelper implements Serializable {
    public Map<Integer, Integer> fishToTile = new HashMap<>();

    public Map<Integer, Integer> tileToMatchingTile = new HashMap<>();

    public Map<Integer, Boolean> fishToTileState = new HashMap<>();

    public Boolean isThereFlippedTile = false;

    public Integer flippedTile = 0;

    public Integer flippedFish = 0;

}
