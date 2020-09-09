package edu.cmu.cs.cs214.hw4.core;

/**
 * Carcassonne main program
 */
public class PlayCarcassonneCore {
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
        game.selectFeature(0);
        game.placeMeeple();
        //t2
        game.rotateTile();
        game.selectLocation(1,0);
        game.placeTile();
        game.selectFeature(2);
        game.placeMeeple();
        //t3
        game.rotateTile();
        game.selectLocation(-1,0);
        game.placeTile();
        game.skipPlaceMeeple();
        //t4
        game.rotateTile();
        game.selectLocation(1,-1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        // t5
        game.selectLocation(0,-2);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        //t6
        game.rotateTile();
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(1,-2);
        game.placeTile();
        game.selectFeature(1);
        game.placeMeeple();
        //t7
        game.selectLocation(0,1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        //t8
        game.rotateTile();
        game.selectLocation(-1,-1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        //t9
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(-1,1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        //t10
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(0,2);
        game.placeTile();
        game.skipPlaceMeeple();
        //t11
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(1,2);
        game.placeTile();
        game.selectFeature(1);
        game.placeMeeple();
        //t12
        game.selectLocation(1,1);
        game.placeTile();
        game.skipPlaceMeeple();
        //t13
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(-1,-2);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple(); // expected illegal meeple placement
        game.skipPlaceMeeple();
    }
}
