package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.GameAPI;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameStartWindow implements GameChangeListener {
    private static final int WINDOW_HEIGHT = 845;
    private static final int WINDOW_WIDTH = 1045;
    private static final int GUIDE_LEFT = 450;
    private static final int GUIDE_RIGHT = 650;
    private static final int GUIDE_TOP = 645;
    private static final int START_DIALOG_BOX_HEIGHT = 30;
    private static final int START_DIALOG_BOX_WIDTH = 100;
    private static final Color ADD_BUTTON_COLOR = Color.GREEN;
    private static final int ADD_BUTTON_WIDTH = 80;
    private BufferedImage gameStartImage = ImageIO.read(new File("src/main/resources/carcassonne2.jpg"));

    private JFrame window;
    private GameAPI game;
    private JPanel startPanel;
    private List<String> allPlayerID;

    public GameStartWindow(JFrame window, GameAPI game) throws IOException {
        this.window = window;
        this.game = game;
        initWindow();
    }

    private void initWindow() {
        allPlayerID = new ArrayList<>();

        // Setting up components
        JLabel playersLabel = new JLabel("Player ID: ");
        final JTextField enterPlayerID = new JTextField(100);
        enterPlayerID.setBounds(GUIDE_LEFT, GUIDE_TOP, START_DIALOG_BOX_WIDTH, START_DIALOG_BOX_HEIGHT);

        JButton addPlayerButton = new JButton("Add");
        addPlayerButton.setBackground(ADD_BUTTON_COLOR) ;
        addPlayerButton.setBounds(GUIDE_RIGHT, GUIDE_TOP, ADD_BUTTON_WIDTH, START_DIALOG_BOX_HEIGHT);

        addPlayerButton.addActionListener( e -> {
            String id = enterPlayerID.getText().trim();
            if (!id.isEmpty() && !allPlayerID .contains(id)) {
                allPlayerID.add(id);
                game.newPlayer(id);
            }
            enterPlayerID.setText("");
            enterPlayerID.requestFocus();
        });

        JButton startButton = new JButton("Start");
        startButton.setBackground(ADD_BUTTON_COLOR) ;
        startButton.setBounds(GUIDE_RIGHT-ADD_BUTTON_WIDTH, GUIDE_TOP,
                                     ADD_BUTTON_WIDTH, START_DIALOG_BOX_HEIGHT);

        startButton.addActionListener( e -> {
            game.startGame();
            enterPlayerID.setText("");
            enterPlayerID.requestFocus();
        });

        // Setting up background image;
        JLabel background = new JLabel();
        ImageIcon icon = new ImageIcon(gameStartImage.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT,
                                       Image.SCALE_SMOOTH));
        background.setBounds(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        background.setIcon(icon);

        JLabel startPanel = new JLabel();
        startPanel.setBounds(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        startPanel.setLayout(null);

        startPanel.add(addPlayerButton);
        startPanel.add(startButton);
        startPanel.add(enterPlayerID);
        startPanel.add(background);
        window.add(startPanel);
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
    public void playerChanged(int playerIndex) {

    }

    /**
     * Called when the score of the players has changed
     *
     * @param scoreBoard the mapping from user names to scores
     */
    @Override
    public void scoreChanged(Map<String, List<Integer>> scoreBoard) {

    }

    /**
     * Called when the game start is illegal
     */
    @Override
    public void illegalGameStart() {
        JOptionPane.showMessageDialog(null, "Game needs 2-5 players to start!");
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
     * Called when the game has been restarted
     */
    @Override
    public void gameRestarted() {
        window.getContentPane().removeAll();
        window.getContentPane().invalidate();
        initWindow();
        window.getContentPane().revalidate();
    }

    /**
     * Called when the game has ended
     */
    @Override
    public void gameEnded() {

    }
}
