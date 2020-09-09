package edu.cmu.cs.cs214.hw4.core;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for {@link LoadGameData}
 *
 * @author Vincent Mai
 */
public class TestLoadGameData {
    private static  LoadGameData.TileData startTileData;
    private static Segment.Type[] segmentType;
    private static Feature.Type[] featureType;

    /**
     * run before all unit test
     */
    @BeforeClass
    public static void setup() {
        startTileData = new LoadGameData.TileData(
                "Dummy Type", 0,1, 0,
                "R C R F",
                "r c m",
                "2 1 0",
                "0 1 0 -1"
        );
        segmentType = new Segment.Type[]{
                Segment.Type.ROAD,
                Segment.Type.CITY,
                Segment.Type.ROAD,
                Segment.Type.FIELD
        };
        featureType = new Feature.Type[]{
                Feature.Type.ROAD,
                Feature.Type.CITY,
                Feature.Type.MONASTERY
        };
    }

    /**
     * test {@link LoadGameData.TileData}
     */
    @Test
    public void TestTileData() {
        assertEquals(startTileData.type, "Dummy Type");
        assertEquals(startTileData.numOfTiles, 1);
        assertEquals(startTileData.shields, 0);
        assertArrayEquals(startTileData.segmentType, segmentType);
        assertArrayEquals(startTileData.featureType, featureType);
    }
}
