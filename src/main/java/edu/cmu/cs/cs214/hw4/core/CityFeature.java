package edu.cmu.cs.cs214.hw4.core;

/**
 * CityFeature class represents city features in Carcassonne.
 *
 * @author Vincent Mai
 */
public class CityFeature extends ExtendableFeature implements Feature {
    private static final int COMPLETE_POINTS_PER_TILE = 2;
    private static final int INCOMPLETE_POINTS_PER_TILE = 1;
    private static final int COMPLETE_POINTS_PER_SHIELD = 2;
    private static final int INCOMPLETE_POINTS_PER_SHIELD = 1;
    private int shields;

    /**
     * Constructor of city features
     * 
     * @param  type  the feature type
     * @param tile  the initial tile where the city exists
     * @param numExtendableSegments  number of segments by which the feature can be extended
     */
    public CityFeature(Type type, Tile tile, int numExtendableSegments, int shields) {
        super(type, tile, numExtendableSegments);
        this.shields = shields;
    }

    /**
     * Merging a new feature f with itself. The merged feature contains updated
     * number of tiles, extendable segments, shields and meeples as the result of merger.
     * @param f  new feature to be subsumed
     */
    @Override
    public void merge(Feature f) {
        super.merge(f);
        CityFeature other = (CityFeature) f;
        this.shields += other.shields;
    }

    /**
     * Scores the feature and returns meeples and points to players
     * based on whether the feature is completed
     *
     * @param board the game board
     */
    @Override
    public void score(Board board) {
        for (Meeple mp : getMostMeeple()) {
            int pointsPerTile = completed(board)? COMPLETE_POINTS_PER_TILE : INCOMPLETE_POINTS_PER_TILE;
            int pointsPerShield = completed(board)? COMPLETE_POINTS_PER_SHIELD : INCOMPLETE_POINTS_PER_SHIELD;
            int points = tiles.size() * pointsPerTile + shields * pointsPerShield;
            mp.getPlayer().reward(points);
            mp.returnToPlayer(meeples.get(mp));
        }
    }
}
