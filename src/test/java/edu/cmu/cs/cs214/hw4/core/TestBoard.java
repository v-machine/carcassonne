package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit testing of {@link Board}
 *
 * @author Vincent Mai
 */
public class TestBoard {
    private static Tile startTile = LoadTestData.startTile;
    private static Board board;
    private static List<Coord> legalPlacement = new ArrayList<>();
    private static Tile fTile = LoadTestData.stack.get(5);

    /**
     * run before all unit test
     */
    @BeforeClass
    public static void setup() {
        legalPlacement.add(new Coord(-1,0));
        legalPlacement.add(new Coord(0,1));
        legalPlacement.add(new Coord(1,0));
        legalPlacement.add(new Coord(0,-1));
    }

    @Before
    public void runBeforeEach() {
         board = new Board(startTile);
    }

    /**
     * Test {@link Board#getPlacementLocs()}
     */
    @Test
    public void testGetPlacementLocs() {
        assertArrayEquals(board.getPlacementLocs().toArray(), legalPlacement.toArray());
    }

    /**
     * Test {@link Board#actionAvailable(Tile)}
     */
    @Test
    public void TestActionAvailable() {
        Tile dummy_StartTile = LoadTestData.bTileStartTile;  // Monastery surrounded by fields
        Board dummy_board = new Board(dummy_StartTile);
        assertFalse(dummy_board.validTilePlacement(startTile));
    }

    /**
     * Test{@link Board#getTilesLoc()}
     */
    @Test
     public void TestGetTilesLoc() {
         fTile.place(new Coord(0,1), board);
         board.add(fTile);
         Set<Coord> location = board.getTilesLoc();
         assert(location.contains(new Coord(0,0)));
         assert(location.contains(new Coord(0,1)));
     }

    /**
     * Test {@link Board#findJointExistingFeatures(Feature)}
     */
    @Test
     public void TestFindJointExistingFeatures() {
         Feature city = fTile.getFeatures().get(0);
         fTile.place(new Coord(0,1), board);
//         assertEquals(1, board.findJointExistingFeatures(city).size());
//         assertEquals("CITY", board.findJointExistingFeatures(city).get(0).toString());
     }
}
