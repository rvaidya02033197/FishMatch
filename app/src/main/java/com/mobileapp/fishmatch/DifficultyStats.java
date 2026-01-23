package com.mobileapp.fishmatch;

public class DifficultyStats {
    // public variables containing stats
    public int gamesPlayed;
    public long fastestTime;
    public int pointsScored;
    public int leastFlips;

    // explicit default constructor
    public DifficultyStats() {
        gamesPlayed = 0;
        fastestTime = Long.MAX_VALUE;
        pointsScored = 0;
        leastFlips = Integer.MAX_VALUE;
    }
}
