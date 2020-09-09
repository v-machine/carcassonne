package edu.cmu.cs.cs214.hw4.core;

/**
 * Player class stores the user name, meeples, and score
 * of the player.
 *
 * @author Vincent Mai
 */
public class Player {
    private Meeple meeples;
    private String userName;
    private int score = 0;
    private Tile tile;

    /**
     * Constructor of player
     * @param userName  player's name
     */
    Player(String userName) {
        this.userName = userName;
        meeples = new Meeple(this);
    }

    /**
     * Returns player's meeples
     *
     * @return meeples of the player
     */
    public Meeple getMeeples() {
        return meeples;
    }

    /**
     * Returns player's meeples
     *
     * @return meeples of the player
     */
    public int meeplesLeft() {
        return meeples.meeplesLeft();
    }

    /**
     * Reward player with a given score
     * @param points  the rewarding score
     */
    void reward(int points) {
        score += points;
    }

    /**
     * Returns the current score of the player
     * @return  score  the player's score
     */
    public int getScore() { return score; }

    /**
     * Override toString method to display player name
     * @return
     */
    @Override
    public String toString() {
        return userName;
    }
}