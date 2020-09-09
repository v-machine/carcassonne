package edu.cmu.cs.cs214.hw4.core;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class Game implements GameAPI {
    private static Random randomSeed = new Random(0); //TODO: remove random seed
    private final List<GameChangeListener> gameChangeListeners = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private Player curPlayer;
    private List<Tile> stack;
    private Tile startTile, currentTile;
    private Feature selectedFeature;
    private int drawIndex = 0;
    private int playerIndex = 0;
    private Board board;
    private Coord placementLoc;
    private int turnNum = 1;
    private Map<Point, Integer> scoredTiles;

    /**
     * Adding new listeners to notify when the game state is change
     * @param listener  a subscriber for the game change
     */
    @Override
    public void addGameChangeListener(GameChangeListener listener) {
        gameChangeListeners.add(listener);
    }

    /**
     * Creates new players and adds to the
     *
     * @param userName of the player
     */
    @Override
    public void newPlayer(String userName) {
        players.add(new Player(userName));
        if (players.size() > 5) {
            notifyIllegalGameStart();
            players.remove(players.size()-1);
        } else {
            System.out.println("Added new player " + userName);
        }
    }

    /**
     * Initialize game components and starts the game
     */
    @Override
    public void startGame() {
        if (players.size() >= 2) {
            loadPlayers();
            loadStack();
            loadBoard();
            notifyGameStarted();
            notifyScoreChanged();
            newTurn();
        } else {
            notifyIllegalGameStart();
        }
    }

    /**
     * Clear game cache and restart
     */
    @Override
    public void restartGame() {
        players = new ArrayList<>();
        curPlayer = null;
        stack = null;
        startTile = null;
        currentTile = null;
        selectedFeature = null;
        drawIndex = 0;
        playerIndex = 0;
        board = null;
        Coord placementLoc;
        turnNum = 1;
        scoredTiles = null;
        notifyGameRestarted();
    }


    /**
     * Returns a list of possible placement locations given the
     * current board configuration
     *
     * @return  placement locations
     */
    @Override
    public List<Point> getPlacementLocs() {
        List<Point> placementLocations = new ArrayList<>();
        for (Coord location : board.getPlacementLocs()) {
            int x = location.getX();
            int y = location.getY();
            placementLocations.add(new Point(x, y));
        }
        return placementLocations;
    }

    /**
     * Selects one of the possible board locations to place tile
     *
     * @param x  the row of the tile
     * @param y  the col of the tile
     */
    @Override
    public void selectLocation(int x, int y) {
        placementLoc = new Coord(x, y);
        System.out.println("SELECTED LOCATION: ("+x+", "+y+")");
    }

    /**
     * Rotates the tile by 90 degree CCW
     */
    @Override
    public void rotateTile() {
        currentTile.rotate();
        System.out.print("ROTATED: ");
        printTile();
    }

    /**
     * Places the tile on the board
     */
    @Override
    public void placeTile() {
        try {
            currentTile.place(placementLoc, board);
            notifyTilePlaced();
            System.out.print("PLACED: ");
            printTile();
        } catch (Exception e) {
            System.out.println("Invalid Tile Placement");
            notifyIllegalTilePlacement();
        }
    }

    /**
     * Selects one feature from the tile just placed
     * @param idx  index of the feature
     */
    @Override
    public void selectFeature(int idx) {
        System.out.println("SELECTED FEATURE idx: "+idx);
        selectedFeature = currentTile.getFeatures().get(idx);
    }

    /**
     * Player of the current turn places a meeple on the selected feature
     */
    @Override
    public void placeMeeple() {
        try {
            System.out.println("PLAYER PLACING MEEPLE: "+curPlayer.toString());
            curPlayer.getMeeples().place(selectedFeature, board);
            System.out.println("Meeple of "+curPlayer+" placed on "+selectedFeature);
            notifyMeeplePlaced();
            endTurn();
            newTurn();
        } catch (Exception e) {
            System.out.println("Invalid Meeple Placement");
            notifyIllegalMeeplePlacement();
        }
    }

    /**
     * Player of the current turn skips meeple placement
     */
    @Override
    public void skipPlaceMeeple() {
        endTurn();
        newTurn();
    }

    /**
     * Return the current player of the turn
     * @return  curPlayer the user name of the current player
     */
    public String getCurPlayer() {
        return curPlayer.toString();
    }

    /**
     * Return a string array representing the features of the placed tile
     * @return  an array of features
     */
    public String[] getTileFeatures() {
        int size = currentTile.getFeatures().size();
        String[] tileFeatures = new String[size];
        for (int i = 0; i < size; i++) {
            tileFeatures[i] = currentTile.getFeatures().get(i).toString();
        }
        return tileFeatures;
    }

    private void notifyIllegalGameStart() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.illegalGameStart();
        }
    }

    private void notifyGameStarted() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.gameStarted();
        }
    }

    private void notifyGameRestarted(){
        for (GameChangeListener listener : gameChangeListeners) {
            listener.gameRestarted();
        }
    }

    private void notifyTurnStarted() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.turnStarted(currentTile.getIndex(), getPlacementLocs());
        }
    }

    private void notifyPlayerChanged() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.playerChanged(playerIndex);
        }
    }

    private void notifyIllegalTilePlacement() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.illegalTilePlaced();
        }
    }

    private void notifyIllegalMeeplePlacement() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.illegalMeeplePlaced();
        }
    }

    private void notifyActionUnavailable() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.actionUnavailable();
        }
    }

    private void notifyTilePlaced() throws InvocationTargetException, InterruptedException {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.tilePlaced();
        }
    }

    private void notifyMeeplePlaced() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.meeplePlaced();
        }
    }

    private void notifyScoreChanged() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.scoreChanged(getScoreBoard());
        }
    }

    private void notifyTurnEnded() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.turnEnded(scoredTiles);
        }
    }

    private void notifyGameEnded()  {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.gameEnded();
        }
    }

    /**
     * Starts the turn by updating the current player and drawing
     * a new tile from the stack
     */
    private void newTurn() {
        if (gameOver()) {
            scoreFinal();
            notifyScoreChanged();
            notifyGameEnded();
        } else {
            nextPlayer();
            System.out.println("/// NEW TURN ["+turnNum+"]: "+curPlayer+" ///");
            drawTile();
            while (!board.actionAvailable(currentTile) && numTilesRemain() > 0) {
                System.out.println("No action available with current draw!");
                notifyActionUnavailable();
                drawTile();
            }
            notifyTurnStarted();
        }
    }

    /**
     * Ends the turn by adding a valid tile (and meeple) placement
     * to the board, scores all completed features and returns meeples
     */
    private void endTurn() {
        board.add(currentTile);
        List<Feature> scoredFeatures = board.scoreCompletedFeatures();
        scoredTiles = new HashMap<>();
        for (Feature f : scoredFeatures) {
            for (Tile t : f.getTiles()) {
                Point loc = new Point(t.getLocation().getX(), t.getLocation().getY());
                int tileIndex = t.getIndex();
                scoredTiles.put(loc, tileIndex);
            }
        }
        reset();
        System.out.println("/// TURN ENDED ///");
        notifyScoreChanged();
        notifyTurnEnded();
        for (Player p: players){
            System.out.println(p+" score: "+p.getScore());
        }
        notifyScoreChanged();
        turnNum++;
    }

    /**
     * Scores all incomplete features at the end of the game
     */
    private void scoreFinal() {
        board.scoreFinalFeatures();
    }

    private void loadPlayers() {
        Collections.shuffle(players, randomSeed);
    }

    private void loadStack() {
        LoadGameData gameData = new LoadGameData();
        stack = new ArrayList<>();
        startTile = new Tile(gameData.startTileData, Board.BOARD_ORIGIN);
        for (LoadGameData.TileData data : gameData.stackData) {
            for (int i = 0; i < data.numOfTiles; i++) {
                stack.add(new Tile(data));
            }
        }
        Collections.shuffle(stack, randomSeed);
    }

    private void loadBoard() {
        board = new Board(startTile);
        System.out.println("New Board: "+startTile);
    }

    private void drawTile() {
        currentTile = stack.get(drawIndex);
        drawIndex++;
        System.out.print("DRAWN: ");
        printTile();
    }

    private void nextPlayer() {
        playerIndex %= players.size();
        curPlayer = players.get(playerIndex);
        notifyPlayerChanged();
        playerIndex++;
    }

    private void reset() {
        placementLoc = null;
        currentTile = null;
        selectedFeature = null;
    }

    private int numTilesRemain() {
        return stack.size() - drawIndex - 1;
    }

    private boolean gameOver() {
        return numTilesRemain() == 0;
    }

    private void printTile() {
        System.out.println(currentTile);
    }

    public List<Integer> getCurrentScores() {
        List<Integer> scores = new ArrayList<>();
        for (Player p : players) {
            scores.add(p.getScore());
        }
        return scores;
    }

    public Map<String, List<Integer>> getScoreBoard() {
        Map<String, List<Integer>> scoreBoard = new HashMap<>();
        for (Player p : players) {
            List<Integer> playerStatus = new ArrayList<>();
            playerStatus.add(p.meeplesLeft());
            playerStatus.add(p.getScore());
            scoreBoard.put(p.toString(), playerStatus);
        }
        return scoreBoard;
    }
}