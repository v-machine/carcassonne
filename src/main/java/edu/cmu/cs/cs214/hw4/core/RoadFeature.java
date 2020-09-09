package edu.cmu.cs.cs214.hw4.core;

public class RoadFeature extends ExtendableFeature implements Feature {
    private static final int COMPLETE_POINTS_PER_TILE = 1;
    private static final int INCOMPLETE_POINTS_PER_TILE = 1;
    /**
     * Constructor of road feature
     *
     * @param  type  the feature type
     * @param tile  the initial tile where the road exists
     * @param numExtendableSegments  number of segments by which the feature can be extended
     */
    public RoadFeature(Type type, Tile tile, int numExtendableSegments) {
        super(type, tile, numExtendableSegments);
    }

    /**
     * Scores the feature and returns meeples and points to players
     * based on whether the feature is completed

     * @param board the game board
     */
    @Override
    public void score(Board board) {
        for (Meeple mp : getMostMeeple()) {
            int pointsPerTile = completed(board)? COMPLETE_POINTS_PER_TILE : INCOMPLETE_POINTS_PER_TILE;
            int points = tiles.size() * pointsPerTile;
            mp.getPlayer().reward(points);
            mp.returnToPlayer(meeples.get(mp));
        }
    }
}
