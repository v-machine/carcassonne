package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit testing for {@link Tile}
 *
 * @author Vincent Mai
 */

public class TestTile {
    private static Tile startTile = LoadTestData.startTile;
    private static Tile cTile = LoadTestData.stack.get(2);
    private static Board board;

    @Before
    public void runBeforeEach() {
        board = new Board(startTile);
    }

    /**
     * Test {@link Tile#Tile(LoadGameData.TileData, Coord)}
     */
    @Test
    public void TestTileInit_on_startTile() {
        // using toString to test init correctness
        String startTileRepr = "Tile: D on (0, 0) with segments: ROAD(-1, 0) CITY(0, 1) " +
                "ROAD(1, 0) FIELD(0, -1)  with features: ROAD CITY ";
        assertEquals(startTile.toString(), startTileRepr);
    }

    /**
     * Test {@link Tile#getFeatures()}
     */
    @Test
    public void TestGetFeatures() {
        List<Feature> features = cTile.getFeatures();
        assertEquals(features.size(), 1);
        assertEquals(features.get(0).toString(),"CITY");
    }

    /**
     * Test {@link Tile#rotate()} and {@link Tile#undoAllRotate()}
     */
    @Test
    public void TestRotate_undoRotate() {
        String afterRotateRepr = "Tile: D on (0, 0) with segments: FIELD(-1, 0) ROAD(0, 1) " +
                "CITY(1, 0) ROAD(0, -1)  with features: ROAD CITY ";
        String undoAllRotateRepr = "Tile: D on (0, 0) with segments: ROAD(-1, 0) CITY(0, 1) " +
                "ROAD(1, 0) FIELD(0, -1)  with features: ROAD CITY ";
        startTile.rotate();
        assertEquals(startTile.toString(), afterRotateRepr);
        startTile.undoAllRotate();
        assertEquals(startTile.toString(), undoAllRotateRepr);
    }

    /**
     * Test {@link Tile#place(Coord, Board)} and {@link Tile#undoPlace()}
     */
    @Test
    public void TestPlace_unPlace() {
        cTile.place(new Coord(0,1), board);
        assertEquals(new Coord(0,1), cTile.getLocation());
        cTile.undoPlace();
        assertNull(cTile.getLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void Test_illegalTilePlacement() {
        cTile.place(new Coord(0,0), board);
        cTile.place(new Coord(2,0), board);
    }

    /**
     * Test {@link Tile#getSegment(Coord)}
     */
    @Test
    public void TestGetSegment() {
        assertEquals(new Segment(Segment.Type.CITY), cTile.getSegment(new Coord(-1, 0)));
    }

    /**
     * Test {@link Tile#getLinkedSegments(Feature)}
     */
    @Test
    public void TestGetLinkedSegments() {
        List<Segment> linkedSegments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            linkedSegments.add(new Segment(Segment.Type.CITY));
        }
        Feature f = cTile.getFeatures().get(0);
        Assert.assertEquals(linkedSegments, cTile.getLinkedSegments(f));
    }
}
