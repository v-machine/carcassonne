package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.GameAPI;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GamePlayWindow implements GameChangeListener {
    private static final int WINDOW_HEIGHT = 845;
    private static final int WINDOW_WIDTH = 1045;
    private static final int BOARD_SIZE = 1500;
    private static final int BOARD_VIEWPORT_SIZE = 810;
    private static final int SIDE_BAR_GUIDE = 20;
    private static final int SIDE_BAR_WIDTH = 225;
    private static final int SIDE_BAR_START = 810;
    private static final int ROW_MARGIN = 10;
    private static final int TILEDISP_PANEL_HEIGHT = 300;
    private static final int ACTION_PANEL_START = 300;
    private static final int ACTION_PANEL_HEIGHT = 100;
    private static final int PLAYER_PANEL_HEIGHT = 60;
    private static final int CURRENT_TILE_SIZE = 160;
    private static final int TILE_ON_BOARD_SIZE = 90;
    private static final int START_TILE_XY = BOARD_SIZE/2-TILE_ON_BOARD_SIZE/2;
    private static final int BUTTON_HEIGHT = 35;
    private static final int BUTTON_WIDTH = 85;
    private static final int SCROLL_SPEED = 20;
    private static final int START_TILE_INDEX = 3;
    private static final Color BOARD_COLOR = Color.DARK_GRAY;
    private static final Color SIDE_BAR_COLOR = Color.LIGHT_GRAY;
    private static final Point VIEWPORT_POS = new Point(BOARD_SIZE/2-BOARD_VIEWPORT_SIZE/2,
                                                        BOARD_SIZE/2-BOARD_VIEWPORT_SIZE/2);
    private static final int MEEPLE_SIZE = 8;
    private static final Color[] MEEPLE_COLORS = new Color[]{Color.MAGENTA, Color.YELLOW,
                                                             Color.CYAN, Color.RED, Color.BLUE};
    private JFrame window;
    private GameAPI game;
    private Assets gameAssets = new Assets();
    private Map<Point, JLabel> placedTiles;
    private Map<Point, JLabel> placedTilesNoMeeple;
    private Map<Point, JButton> placementLocations;
    private Map<String, List<Integer>> scoreBoard;
    private JPanel gameBoard;
    private JPanel tileDispPanel;
    private JPanel actionPanel;
    private JPanel scoreBoardPanel;
    private JLabel currentTile;
    private JScrollPane scroll;
    private BufferedImage currentTileImage;
    private List<BufferedImage> currentTileFeatureImages;
    private int currentTileIndex;
    private int currentTileFeatureIndex;
    private int currentPlayerIndex;
    private Point currentTilePlacement;

    public GamePlayWindow(JFrame window, GameAPI game) throws IOException {
        this.window = window;
        this.game = game;
        gameAssets.load();
    }

    private void initGameBoard() {
        gameBoard = new JPanel();
        gameBoard.setLayout(null);
        gameBoard.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        gameBoard.setBackground(BOARD_COLOR);
        scroll = new JScrollPane();
        scroll.setBounds(0, 0, BOARD_VIEWPORT_SIZE, BOARD_VIEWPORT_SIZE);
        scroll.setViewportView(gameBoard);
        scroll.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scroll.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scroll.getViewport().setViewPosition(new Point(VIEWPORT_POS));
        placementLocations = new HashMap<>();
        placedTiles = new HashMap<>();
        placedTilesNoMeeple = new HashMap<>();
        currentTileFeatureImages = new ArrayList<>();
        window.add(scroll);
    }

    private void initSideBar() {
        tileDispPanel = new JPanel();
        tileDispPanel.setLayout(null);
        tileDispPanel.setBounds(SIDE_BAR_START, 0, SIDE_BAR_WIDTH, TILEDISP_PANEL_HEIGHT);
        tileDispPanel.setBackground(SIDE_BAR_COLOR);

        actionPanel = new JPanel();
        actionPanel.setLayout(null);
        actionPanel.setBounds(SIDE_BAR_START, ACTION_PANEL_START, SIDE_BAR_WIDTH, 100);
        actionPanel.setBackground(MEEPLE_COLORS[currentPlayerIndex]);

        scoreBoardPanel = new JPanel();
        scoreBoardPanel.setLayout(null);
        scoreBoardPanel.setBounds(SIDE_BAR_START, 410, SIDE_BAR_WIDTH, WINDOW_HEIGHT-400);
        scoreBoardPanel.setBackground(SIDE_BAR_COLOR);

        scoreBoard = new HashMap<>();
        drawPlaceTilePanel();
        drawScoreBoardPanel();
        window.getContentPane().add(tileDispPanel);
        window.getContentPane().add(actionPanel);
        window.getContentPane().add(scoreBoardPanel);
    }

    @Override
    /**
     * Called when a new round is initiated
     * @param tileIndex  the index of the newly drawn tile
     * @param placementLocs  the current possible board placements
     */
    public void turnStarted(int tileIndex, List<Point> placementLocs) {
        currentTileIndex = tileIndex;
        currentTileImage = gameAssets.getTileImage(currentTileIndex);
        getFeatureImages(currentTileIndex);
        drawPlacementLocs(placementLocs);
        updateCurTile();
        drawPlaceTilePanel();
    }

    /**
     * Called when a new round has ended, redraw scored tiles without meeples
     * @param scoredTiles a map of location and tile index of the scored tiles
     */
    public void turnEnded(Map<Point, Integer> scoredTiles) {
        for (Point loc : scoredTiles.keySet()) {
            Point guiLoc = new Point(loc.y, loc.x);  //turn row/col from game into x/y in GUI
            gameBoard.remove(placedTiles.get(guiLoc));
            updateAll();
            gameBoard.add(placedTilesNoMeeple.get(guiLoc));
            updateAll();
        }
    }

    /**
     * Called when a new tile is placed on the board, including
     * initialization of a new board
     *
     */
    @Override
    public void tilePlaced() {
        for (JButton emptyTile : placementLocations.values()) {
            gameBoard.remove(emptyTile);
            updateAll();
        }
        drawPlacedTile(currentTilePlacement);
        drawPlaceMeeplePanel();
    }

    /**
     * Called when a new meeple is placed on the tile
     */
    @Override
    public void meeplePlaced() {
//        placedTilesNoMeepleIcons.put(currentTilePlacement, currentTileImage);
        currentTileImage = currentTileFeatureImages.get(currentTileFeatureIndex);
        ImageIcon icon = new ImageIcon(currentTileImage.getScaledInstance(
                                       TILE_ON_BOARD_SIZE, TILE_ON_BOARD_SIZE,
                                       Image.SCALE_SMOOTH));
        placedTiles.get(currentTilePlacement).setIcon(icon);
        updateAll();
    }

    /**
     * Called when the current player of the turn changes
     *
     * @param playerIndex the current player's index
     */
    @Override
    public void playerChanged(int playerIndex) {
        currentPlayerIndex = playerIndex;
        actionPanel.setBackground(MEEPLE_COLORS[currentPlayerIndex]);
    }

    /**
     * Called when the score of the players has changed
     *
     * @param scoreBoard the mapping from user names to scores
     */
    @Override
    public void scoreChanged(Map<String, List<Integer>> scoreBoard) {
        this.scoreBoard = scoreBoard;
        updateScoreBoard();
    }

    /**
     * Called when the game start is illegal
     */
    @Override
    public void illegalGameStart() {

    }

    /**
     * Called when the tile placement is illegal
     */
    @Override
    public void illegalTilePlaced() {
        JOptionPane.showMessageDialog(null, "Invalid Tile Placement");
    }

    /**
     * Called when the meeple placement is illegal
     */
    @Override
    public void illegalMeeplePlaced() {
        JOptionPane.showMessageDialog(null, "Invalid Meeple Placement");
    }

    /**
     * Called when a newly drawn tile cannot be placed on the board
     */
    public void actionUnavailable() {
        JOptionPane.showMessageDialog(null, "Tile discarded, action unavailable");
    }

    /**
     * Called when the game has started
     */
    @Override
    public void gameStarted() {
        initWindow();
        currentTileIndex = START_TILE_INDEX;
        currentTileImage = gameAssets.getTileImage(START_TILE_INDEX);
        getFeatureImages(currentTileIndex);
        drawPlacedTile(new Point(0, 0));
        drawCurTile();
    }

    /**
     * Called when the game has been restarted
     */
    @Override
    public void gameRestarted() {

    }

    /**
     * Called when the game has ended
     */
    @Override
    public void gameEnded() {
    }

    private void initWindow() {
        window.getContentPane().removeAll();
        window.getContentPane().invalidate();
        initGameBoard();
        initSideBar();
        window.getContentPane().revalidate();
    }

    private void drawPlaceTilePanel() {
        actionPanel.removeAll();
        updateAll();
        JButton rotateButton = new JButton("Rotate");
        rotateButton.setBackground(Color.GREEN);
        rotateButton.setBounds(SIDE_BAR_GUIDE, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        rotateButton.addActionListener(e -> {
            game.rotateTile();
            drawRotatedTile();
            // TODO: also rotate feature images
        });
        JButton placeButton = new JButton("Place");
        placeButton.setBackground(Color.GREEN);
        placeButton.setBounds(SIDE_BAR_GUIDE+10+BUTTON_WIDTH, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        placeButton.addActionListener(e -> {
            game.placeTile();
        });
        actionPanel.add(rotateButton);
        actionPanel.add(placeButton);
    }

    private void drawPlaceMeeplePanel() {
        actionPanel.removeAll();
        updateAll();
        JButton placeMeepleButton = new JButton("Meeple");
        placeMeepleButton.setBackground(Color.GREEN);
        placeMeepleButton.setBounds(SIDE_BAR_GUIDE, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        placeMeepleButton.addActionListener(e -> {
            game.placeMeeple();
        });
        JButton skipMeepleButton = new JButton("Skip");
        skipMeepleButton.setBackground(Color.GREEN);
        skipMeepleButton.setBounds(SIDE_BAR_GUIDE+10+BUTTON_WIDTH, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        skipMeepleButton.addActionListener(e -> {
            game.skipPlaceMeeple();
        });

        JComboBox<String> featureMenu = new JComboBox<>();
        featureMenu.setBounds(SIDE_BAR_GUIDE, 50, BUTTON_WIDTH*2+10, BUTTON_HEIGHT);
        String[] featureTypes = game.getTileFeatures();
        for (int i = 0; i < featureTypes.length ; i++) {
            featureTypes[i] = featureTypes[i] + " " + i;
            featureMenu.addItem(featureTypes[i]);
        }
        featureMenu.addActionListener(e -> {
            int featureIndex = featureMenu.getSelectedIndex();
            game.selectFeature(featureIndex);
            currentTileFeatureIndex = featureIndex;
            showFeature(featureIndex);
        });
        actionPanel.add(placeMeepleButton);
        actionPanel.add(skipMeepleButton);
        actionPanel.add(featureMenu);
    }

    private void drawScoreBoardPanel() {
        int PLAYER_STATUS_PANEL_HEIGHT = 80;
        int counter = 0;
        for (String player : scoreBoard.keySet()) {
            JLabel playerStatus = new JLabel();
            playerStatus.setBounds(0, PLAYER_STATUS_PANEL_HEIGHT*counter, SIDE_BAR_WIDTH, PLAYER_STATUS_PANEL_HEIGHT);
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(player).append(":").append("<br>");
            sb.append("Meeples:    ").append(scoreBoard.get(player).get(0)).append("<br>");
            sb.append("Scores:    ").append(scoreBoard.get(player).get(1)).append("<br>");
            sb.append("<html>");
            playerStatus.setText(sb.toString());
            playerStatus.setBackground(MEEPLE_COLORS[counter]);
            playerStatus.setOpaque(true);
            scoreBoardPanel.add(playerStatus);
            counter ++;
        }
    }

    private void updateScoreBoard() {
        scoreBoardPanel.removeAll();
        updateAll();
        drawScoreBoardPanel();
    }

    /**
     * draws the current tile
     */
    private void drawCurTile() {
        ImageIcon icon = new ImageIcon(currentTileImage.getScaledInstance(
                                       CURRENT_TILE_SIZE, CURRENT_TILE_SIZE,
                                       Image.SCALE_SMOOTH));
        currentTile = new JLabel();
        currentTile.setBounds(SIDE_BAR_GUIDE, 50,
                              CURRENT_TILE_SIZE, CURRENT_TILE_SIZE);
        currentTile.setIcon(icon);
        tileDispPanel.add(currentTile);
        updateAll();
    }

    private void updateCurTile() {
        ImageIcon icon = new ImageIcon(currentTileImage.getScaledInstance(
                CURRENT_TILE_SIZE, CURRENT_TILE_SIZE,
                Image.SCALE_SMOOTH));
        currentTile.setIcon(icon);
        updateAll();
    }

    private void drawRotatedTile() {
        currentTileImage = Assets.Image.rotateClockwise(currentTileImage, 90);
        rotatedTileFeatures();
        updateCurTile();
    }

    private void rotatedTileFeatures() {
        for (int i = 0; i < currentTileFeatureImages.size(); i++) {
            BufferedImage image = currentTileFeatureImages.get(i);
            currentTileFeatureImages.set(i, Assets.Image.rotateClockwise(image, 90));
        }
    }

    private void showFeature(int featureIndex) {
        currentTileImage = currentTileFeatureImages.get(featureIndex);
        updateCurTile();
    }

    private void getFeatureImages(int currentTileIndex) {
        currentTileFeatureImages.clear();
        for (Point loc : Assets.Features.locations.get(currentTileIndex)) {
            BufferedImage image = gameAssets.getTileImage(currentTileIndex);
            image = Assets.Image.withCircle(image, MEEPLE_COLORS[currentPlayerIndex],
                    (int)(loc.x*0.9), (int)(loc.y*0.9), MEEPLE_SIZE);
            currentTileFeatureImages.add(image);
        }
    }

    private void drawPlacedTile(Point location) {
        ImageIcon icon = new ImageIcon(currentTileImage.getScaledInstance(
                TILE_ON_BOARD_SIZE, TILE_ON_BOARD_SIZE,
                Image.SCALE_SMOOTH));
        JLabel tile = new JLabel();
        int x = START_TILE_XY + location.x * TILE_ON_BOARD_SIZE;
        int y = START_TILE_XY + location.y * TILE_ON_BOARD_SIZE;
        tile.setBounds(x, y, TILE_ON_BOARD_SIZE, TILE_ON_BOARD_SIZE);
        tile.setIcon(icon);
        placedTiles.put(location, tile);
        placedTilesNoMeeple.put(location, tile);
        gameBoard.add(tile);
        updateAll();
    }

    private void drawPlacementLocs(List<Point> placementLocs) {
        // remove placement locations from last tile
        placementLocations.clear();
        for (Point loc : placementLocs) {
            JButton emptyTile = new JButton();
            int row = START_TILE_XY + loc.x * TILE_ON_BOARD_SIZE;
            int col = START_TILE_XY + loc.y * TILE_ON_BOARD_SIZE;
            emptyTile.setBounds(col, row, TILE_ON_BOARD_SIZE, TILE_ON_BOARD_SIZE);
            emptyTile.setBackground(Color.GRAY);
            emptyTile.addActionListener(e -> {
                game.selectLocation(loc.x, loc.y);
                currentTilePlacement = new Point(loc.y, loc.x); // GUI (x,y), Board(row, col)
                for (Point others : placementLocations.keySet()) {
                    placementLocations.get(others).setBackground(Color.GRAY);
                }
                emptyTile.setBackground(Color.GREEN);
            });
            gameBoard.add(emptyTile);
            placementLocations.put(loc, emptyTile);
        }
        updateAll();
    }

    private void updateAll() {
        gameBoard.revalidate();
        gameBoard.repaint();
        tileDispPanel.revalidate();
        tileDispPanel.repaint();
        actionPanel.revalidate();
        actionPanel.repaint();
        scoreBoardPanel.revalidate();
        scoreBoardPanel.repaint();
        window.getContentPane().validate();
    }
}
