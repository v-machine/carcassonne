package edu.cmu.cs.cs214.hw4.core;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface GameAPI {
    /**
     * Adding new listeners to notify when the game state is change
     * @param listener  a subscriber for the game change
     */
    public void addGameChangeListener(GameChangeListener listener);

    /**
     * Creates new players and adds to the
     *
     * @param userName of the player
     */
    public void newPlayer(String userName);

    /**
     * Initialize game components and starts the game
     */
    public void startGame();

    /**
     * Clear game cache and restart
     */
    public void restartGame();

    /**
     * Returns a list of possible placement locations given the
     * current board configuration
     *
     * @return  placement locations
     */
    public List<Point> getPlacementLocs();

    /**
     * Selects one of the possible board locations to place tile
     *
     * @param x  the row of the tile placement
     * @param y  the col of the tile placement
     */
    public void selectLocation(int x, int y);

    /**
     * Rotates the tile by 90 degree CCW
     */
    public void rotateTile();

    /**
     * Places the tile on the board
     */
    public void placeTile();

    /**
     * Selects one feature from the tile just placed
     * @param idx  index of the feature
     */
    public void selectFeature(int idx);

    /**
     * Player of the current turn places a meeple on the selected feature
     */
    public void placeMeeple();

    /**
     * Player of the current turn skips meeple placement
     */
    public void skipPlaceMeeple();

    /**
     * Return a string array representing the features of the placed tile
     * @return  an array of features
     */
    public String[] getTileFeatures();
}
