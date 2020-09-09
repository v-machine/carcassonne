package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The LoadTestData class loads all test data
 */
public class LoadTestData {
    static Tile startTile;
    static Tile bTileStartTile;
    static List<Tile> stack = new ArrayList<>();
    static Board board;
    static{
        loadData();
    }

    private static void loadData() {
        LoadGameData testData = new LoadGameData();
        startTile = new Tile(testData.startTileData, new Coord(0,0));
        bTileStartTile = new Tile(testData.stackData.get(1), new Coord(0,0));
        for (LoadGameData.TileData data: testData.stackData) {
            stack.add(new Tile(data));
        }
        board = new Board(startTile);
    }
}
