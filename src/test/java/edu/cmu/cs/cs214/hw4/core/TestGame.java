package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing simulated gameplay
 */
public class TestGame {
    @Test
    public void TestGamePlay() {
        Game game = new Game();
        game.newPlayer("V.M.");
        game.newPlayer("I.F.");
        game.startGame();
        //t1
        game.selectLocation(0, -1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==0);
        //t2
        game.rotateTile();
        game.selectLocation(1,0);
        game.placeTile();
        game.selectFeature(2);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==0);
        //t3
        game.rotateTile();
        game.selectLocation(-1,0);
        game.placeTile();
        game.skipPlaceMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==3);
        //t4
        game.rotateTile();
        game.selectLocation(1,-1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==3);
        // t5
        game.selectLocation(0,-2);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==3);
        //t6
        game.rotateTile();
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(1,-2);
        game.placeTile();
        game.selectFeature(1);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==6);
        //t7
        game.selectLocation(0,1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==6);
        //t8
        game.rotateTile();
        game.selectLocation(-1,-1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==6);
        //t9
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(-1,1);
        game.placeTile();
        game.selectFeature(0);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==0);
        assert(game.getCurrentScores().get(1)==6);
        //t10
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(0,2);
        game.placeTile();
        game.skipPlaceMeeple();
        assert(game.getCurrentScores().get(0)==8);
        assert(game.getCurrentScores().get(1)==6);
        //t11
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(1,2);
        game.placeTile();
        game.selectFeature(1);
        game.placeMeeple();
        assert(game.getCurrentScores().get(0)==10);
        assert(game.getCurrentScores().get(1)==6);
        //t12
        game.selectLocation(1,1);
        game.placeTile();
        game.skipPlaceMeeple();
        assert(game.getCurrentScores().get(0)==10);
        assert(game.getCurrentScores().get(1)==6);
        //t13
        game.rotateTile();
        game.rotateTile();
        game.selectLocation(-1,-2);
        game.placeTile();
//        game.selectFeature(0);
//        game.placeMeeple(); // expected illegal meeple placement
        game.skipPlaceMeeple();
        assert(game.getCurrentScores().get(0)==19);
        assert(game.getCurrentScores().get(1)==6);
    }
}
