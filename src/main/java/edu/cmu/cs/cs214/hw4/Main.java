package edu.cmu.cs.cs214.hw4;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.GameAPI;
import edu.cmu.cs.cs214.hw4.gui.GameOverWindow;
import edu.cmu.cs.cs214.hw4.gui.GamePlayWindow;
import edu.cmu.cs.cs214.hw4.gui.GameStartWindow;

import javax.swing.*;
import java.io.IOException;

/**
 * The Carcassonne game. Run main to play the game.
 *
 * @author Vincent Mai
 */
public class Main {
    /**
     * Run main program to play the game
     * @param args
     */
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("Carcassonne");
        window.setSize(1045, 845);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(true);
        GameAPI game = new Game();

        GameStartWindow gameStartWindow = new GameStartWindow(window, game);
        GamePlayWindow gamePlayWindow = new GamePlayWindow(window, game);
        GameOverWindow gameOverWindow = new GameOverWindow(window, game);

        game.addGameChangeListener(gameStartWindow);
        game.addGameChangeListener(gamePlayWindow);
        game.addGameChangeListener(gameOverWindow);
        window.setVisible(true);
        window.setResizable(false);
    }
}
