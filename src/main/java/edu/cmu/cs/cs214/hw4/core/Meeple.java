package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

class Meeple {
    private static final int TOTAL_MEEPLES = 7;
    private int inStock = TOTAL_MEEPLES;
    private Player player;

    /**
     * Constructor of meeple
     * @param player  the owner of the meeples
     */
    Meeple(Player player) {
        this.player = player;
    }

    /**
     * Returns the player who owns the meeples
     * @return  player to whom the meeples belong
     */
    Player getPlayer() { return player; }

    /**
     * Returns meeples to players upon scoring
     * @param n  the number of meeples returned
     */
    void returnToPlayer(int n) { inStock = inStock + n; }

    /**
     * Places a meeple on a feature within the tile, if placement is valid.
     * @param f  Feature to be placed upon
     * @throws IllegalArgumentException if place is invalid
     */
    void place(Feature f, Board board) throws IllegalArgumentException{
        List<Feature> jointFeatures = board.findJointExistingFeatures(f);
        for (Feature feature : jointFeatures) {
            if ((!feature.validMeeplePlacement(this)) || (inStock < 0)) {
                throw new IllegalArgumentException("Invalid meeple placement!");
            }
        }
        f.addMeeple(this);
        inStock--;
    }

    /**
     * returns the number of meeples left
     * @return  num of meeples
     */
    int meeplesLeft() {
        return inStock;
    }
}