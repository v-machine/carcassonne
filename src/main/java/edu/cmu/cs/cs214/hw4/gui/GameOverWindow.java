package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.GameAPI;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Displays when the game is over
 */
public class GameOverWindow implements GameChangeListener {
    private static final int WINDOW_HEIGHT = 845;
    private static final int WINDOW_WIDTH = 1045;
    private static final int SCORE_BOARD_WIDTH = 300;
    private static final int SCORE_BOARD_HEIGHT = 600;
    private static final Color SCORE_BOARD_COLOR = Color.DARK_GRAY;
    private static final Color SCORE_BOARD_TEXT_COLOR = Color.YELLOW;
    private static final Color RESTART_BUTTON_COLOR = Color.GREEN;
    private static final int RESTART_BUTTON_WIDTH = 80;
    private static final int RESTART_BUTTON_HEIGHT = 30;
    private BufferedImage gameStartImage = ImageIO.read(new File("src/main/resources/carcassonne3.jpg"));
    private JFrame window;
    private GameAPI game;
    private Label background;
    private JLabel scoreBoardPanel;
    private Map<String, List<Integer>> scoreBoard;

    public GameOverWindow(JFrame window, GameAPI game) throws IOException {
        this.window = window;
        this.game = game;
    }

    private void initWindow(){
        background = new Label();
        background.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        JLabel background = new JLabel();
        ImageIcon icon = new ImageIcon(gameStartImage.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT,
                Image.SCALE_SMOOTH));
        background.setBounds(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        background.setIcon(icon);

        scoreBoardPanel = new JLabel();
        scoreBoardPanel.setLayout(null);
        scoreBoardPanel.setBounds((WINDOW_WIDTH-SCORE_BOARD_WIDTH)/2, (WINDOW_HEIGHT-SCORE_BOARD_HEIGHT)/2,
                                      SCORE_BOARD_WIDTH, SCORE_BOARD_HEIGHT);
        scoreBoardPanel.setBackground(SCORE_BOARD_COLOR);
        scoreBoardPanel.setForeground(SCORE_BOARD_TEXT_COLOR);
        int PLAYER_STATUS_PANEL_HEIGHT = 80;
        int counter = 0;

        for (String player : scoreBoard.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(player).append(":").append("<br>");
            sb.append("Meeples:    ").append(scoreBoard.get(player).get(0)).append("<br>");
            sb.append("Scores:    ").append(scoreBoard.get(player).get(1)).append("<br>");
            sb.append("<html>").append("<br>");
            scoreBoardPanel.setText(sb.toString());
            counter ++;
        }


        JButton restartButton = new JButton("Restart");
        restartButton.setBackground(RESTART_BUTTON_COLOR);
        restartButton.setBounds(WINDOW_WIDTH-RESTART_BUTTON_WIDTH/2, WINDOW_HEIGHT-200,
                                 RESTART_BUTTON_WIDTH, RESTART_BUTTON_HEIGHT);

        restartButton.addActionListener(e -> {
            game.restartGame();
        });

        window.getContentPane().add(background);
        window.getContentPane().add(scoreBoardPanel);
        window.getContentPane().add(restartButton);
    }

    /**
     * Called when a new round is initiated
     *
     * @param tileIndex     the index of the newly drawn tile
     * @param placementLocs the current possible board placements
     */
    @Override
    public void turnStarted(int tileIndex, List<Point> placementLocs) {

    }

    /**
     * Called when a new round has ended, redraw scored tiles without meeples
     *
     * @param scoredTiles a map of location and tile index of the scored tiles
     */
    @Override
    public void turnEnded(Map<Point, Integer> scoredTiles) {

    }

    /**
     * Called when a new tile is placed on the board, including
     * initialization of a new board
     */
    @Override
    public void tilePlaced() {

    }

    /**
     * Called when a new meeple is placed on the tile
     */
    @Override
    public void meeplePlaced() {

    }

    /**
     * Called when the current player of the turn changes
     *
     * @param playerIndex the current player's index
     */
    @Override
    public void playerChanged(int playerIndex) {}

    /**
     * Called when the score of the players has changed
     *
     * @param scoreBoard the mapping from user names to scores
     */
    @Override
    public void scoreChanged(Map<String, List<Integer>> scoreBoard) {
        this.scoreBoard = scoreBoard;
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

    }

    /**
     * Called when the meeple placement is illegal
     */
    @Override
    public void illegalMeeplePlaced() {

    }

    /**
     * Called when a newly drawn tile cannot be placed on the board
     */
    @Override
    public void actionUnavailable() {

    }

    /**
     * Called when the game has started
     */
    @Override
    public void gameStarted() {

    }

    /**
     * Called when the game has ended
     */
    @Override
    public void gameEnded(){
        window.getContentPane().removeAll();
        window.getContentPane().invalidate();
        initWindow();
        window.getContentPane().revalidate();
    }

    /**
     * Called when the game has been restarted
     */
    @Override
    public void gameRestarted() { }

}
