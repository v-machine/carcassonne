package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

/**
 * Feature interface specifies all public methods common to all features
 *
 * @author Vincent Mai
 */
public interface Feature {
    public enum Type {
        ROAD,
        CITY,
        MONASTERY
    }

    /**
     * Checks whether a meeple placement is valid
     * @param mp  a meeple placed by a player
     * @return boolean  true if the placement is valid
     */
    boolean validMeeplePlacement(Meeple mp);

    /**
     * Adds a new meeple to the feature
     * @param mp  meeple to add
     */
    void addMeeple(Meeple mp);

    /**
     * Check if the feature is completed
     *
     * @param  board  game board
     * @return boolean  true if the feature is completed
     */
    boolean completed(Board board);

    /**
     * Merging a new feature and itself. After this method call,
     * calls to the new feature will return this feature.
     * @param f  new feature to be subsumed
     */
    void merge(Feature f);

    /**
     * Scores the feature and returns meeples and points to players
     * based on whether the feature is completed
     *
     * @param board the game board
     */
    void score(Board board);

    /**
     * Returns a list of tiles that feature spans
     *
     * @return tiles  a list of tiles
     */
    List<Tile> getTiles();
}
