package edu.cmu.cs.cs214.hw4.core;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Observer that register the changes in the model
 */
public interface GameChangeListener {
    /**
     * Called when a new round is initiated
     * @param tileIndex  the index of the newly drawn tile
     * @param placementLocs  the current possible board placements
     */
    public void turnStarted(int tileIndex, List<Point> placementLocs);

    /**
     * Called when a new round has ended, redraw scored tiles without meeples
     * @param scoredTiles a map of location and tile index of the scored tiles
     */
    public void turnEnded(Map<Point, Integer> scoredTiles);

    /**
     * Called when a new tile is placed on the board, including
     * initialization of a new board
     */
    void tilePlaced();

    /**
     * Called when a new meeple is placed on the tile
     */
    void meeplePlaced();

    /**
     * Called when the current player of the turn changes
     * @param playerIndex the current player's index
     */
    void playerChanged(int playerIndex);

    /**
     * Called when the score of the players has changed
     * @param scoreBoard  the mapping from user names to scores
     */
    void scoreChanged(Map<String, List<Integer>> scoreBoard);

    /**
     * Called when the game start is illegal
     */
    public void illegalGameStart();

    /**
     * Called when the tile placement is illegal
     */
    void illegalTilePlaced();

    /**
     * Called when the meeple placement is illegal
     */
    void illegalMeeplePlaced();

    /**
     * Called when a newly drawn tile cannot be placed on the board
     */
    void actionUnavailable();

    /**
     * Called when the game has started
     */
    void gameStarted();

    /**
     * Called when the game has been restarted
     */
    public void gameRestarted();

    /**
     * Called when the game has ended
     */
    void gameEnded();
}
