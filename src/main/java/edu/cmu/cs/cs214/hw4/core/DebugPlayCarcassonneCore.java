package edu.cmu.cs.cs214.hw4.core;

/**
 * Debug Carcassonne main program
 */
public class DebugPlayCarcassonneCore {
    /**
     * executes the core of the game without a GUI
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.newPlayer("V.M.");
        game.newPlayer("I.F.");
        game.startGame();
        //t1
        game.selectLocation(0, -1);
        game.placeTile();
        game.skipPlaceMeeple();
        //t2
        game.selectLocation(1,0);
        game.placeTile();
        game.skipPlaceMeeple();
        //t3
        game.selectLocation(-1,-1);
        game.placeTile();
        game.skipPlaceMeeple();
    }
}
