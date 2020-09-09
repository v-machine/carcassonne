package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

public class MonasteryFeature implements Feature {
    private List<Tile> tiles = new ArrayList<>();
    private static final int COMPLETE_POINTS = 9;
    private static final int INCOMPLETE_POINTS_PER_TILE = 1;
    private Meeple meeple = null;
    private Type type;
    private static final Coord[] TILE_LAYOUT = {
            new Coord(-1,0),
            new Coord(0,1),
            new Coord(1,0),
            new Coord(0,-1),
            new Coord(-1,-1),
            new Coord(-1,1),
            new Coord(1,-1),
            new Coord(1,1),
    };

    /**
     * Constructor of monasteries
     * @param tile  the initial tile where the monastery exists
     */
    public MonasteryFeature(Type type, Tile tile) {
        this.type = type;
        tiles.add(tile);
    }

    /**
     * Checks whether a meeple placement is valid
     *
     * @param mp a meeple placed by a player
     * @return boolean  true if the placement is valid
     */
    @Override
    public boolean validMeeplePlacement(Meeple mp) { return meeple == null; }

    /**
     * Adds a new meeple to the feature
     *
     * @param mp meeple to add
     */
    @Override
    public void addMeeple(Meeple mp) {
        if (validMeeplePlacement(mp)) {
            meeple = mp;
        }
    }

    /**
     * Check if the feature is completed
     *
     * @param board  the game board
     * @return boolean  true if the feature is completed
     */
    @Override
    public boolean completed(Board board) {
        Tile centerTile = tiles.get(0);
        for (Coord dir : TILE_LAYOUT) {
            Coord neighborLoc = centerTile.getLocation().add(dir);
            if (!board.getTilesLoc().contains(neighborLoc)) { return false; }
        }
        return true;
    }

    /**
     * Empty method. Monastery does not use merge method
     *
     * @param f new feature to be subsumed
     */
    @Override
    public void merge(Feature f) {}

    /**
     * Scores the feature and returns meeples and points to players
     * based on whether the feature is completed
     *
     * @param board the game board
     */
    @Override
    public void score(Board board) {
        if (meeple == null) { return; }
        if (completed(board)) {
            meeple.getPlayer().reward(COMPLETE_POINTS);
            meeple.returnToPlayer(1);
        }else{
            int numTiles = 0;
            Tile centerTile = tiles.get(0);
            for (Coord dir : TILE_LAYOUT) {
                Coord neighborLoc = centerTile.getLocation().add(dir);
                if (board.getTilesLoc().contains(neighborLoc)) { numTiles += 1; }
            }
            meeple.getPlayer().reward(numTiles * INCOMPLETE_POINTS_PER_TILE);
            meeple.returnToPlayer(1);
        }
    }

    /**
     * Returns a list of tiles
     *
     * @return tiles  a list of tiles
     */
    @Override
    public List<Tile> getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
