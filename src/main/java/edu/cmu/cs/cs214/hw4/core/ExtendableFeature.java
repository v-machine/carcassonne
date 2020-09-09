package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

public abstract class ExtendableFeature implements Feature{
    List<Tile> tiles = new ArrayList<>();
    Map<Meeple, Integer> meeples = new HashMap<>();
    private Type type;
    /**
     * numExtendableSegments are the number of segments by which the feature can be extended
     */
    private int numExtendableSegments;

    /**
     * Constructor of an extendable feature
     *
     * @param  type  the feature type
     * @param tile  the initial tile where the feature exists
     */
    ExtendableFeature(Type type, Tile tile, int numExtendableSegments) {
        this.type = type;
        tiles.add(tile);
        this.numExtendableSegments = numExtendableSegments;
    }

    /**
     * Checks whether a meeple placement is valid
     *
     * @param mp a meeple placed by a player
     * @return boolean  true if the placement is valid
     */
    @Override
    public boolean validMeeplePlacement(Meeple mp) { return this.meeples.containsKey(mp) || meeples.size() == 0; }

    /**
     * Adds a new meeple to the feature if placement is valid
     * @param mp  meeple to add
     */
    public void addMeeple(Meeple mp) {
        if (validMeeplePlacement(mp)) {
            meeples.put(mp, meeples.getOrDefault(mp, 0)+1);
        }
    }

    /**
     * Check if the feature is completed
     *
     ** @param  board  game board
     * @return boolean  true if the feature is completed
     */
    @Override
    public boolean completed(Board board) {
        return numExtendableSegments == 0;
    }

    /**
     * Merging a new feature f with itself. The merged feature contains updated
     * number of tiles, extendable segments and meeples as the result of merger.
     * @param f  new feature to be subsumed
     */
    @Override
    public void merge(Feature f) {
        ExtendableFeature other = (ExtendableFeature) f;
        this.tiles.addAll(other.tiles);
        this.numExtendableSegments = this.numExtendableSegments + other.numExtendableSegments - 2;
        for (Meeple mp : meeples.keySet()) { // merge from this.meeples
            int numMeeples = meeples.get(mp) + other.meeples.getOrDefault(mp, 0);
            meeples.replace(mp, numMeeples);
        }
        for (Meeple mp : other.meeples.keySet()) {// merge from other.meeples
            int numMeeples = meeples.getOrDefault(mp, 0) + other.meeples.get(mp);
            meeples.put(mp, numMeeples);
        }
    }

    /**
     * Scores the feature and returns meeples and points to players
     * based on whether the feature is completed
     *
     * @param board the game board
     */
    public abstract void score(Board board);

    /**
     * Returns a list of tiles that feature spans
     * @return tiles  a list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Returns the meeple(s) with most meeples on the current feature, if tie, return both meeples
     * @return  mostMeeples  the majority of meeples
     */
    List<Meeple> getMostMeeple() {
        List<Meeple> mostMeeples = new ArrayList<>();
        if (meeples.size() == 0) { return mostMeeples; }
        int maxNum = Collections.max(meeples.values());
        for (Meeple mp : meeples.keySet()) {
            if (meeples.get(mp) == maxNum) { mostMeeples.add(mp); }
        }
        return mostMeeples;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
