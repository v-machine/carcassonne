package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Loader for loading all tile data
 */
public class LoadGameData{
    final List<TileData> stackData = new ArrayList<>();
    final TileData startTileData;

    /**
     * Nested TileData class used for initializing data for all tile types
     */
    public static class TileData {
        final String type;
        final int index;
        final int numOfTiles;
        final int shields;
        final Segment.Type[] segmentType;
        final Feature.Type[] featureType;
        final int[] featureExtendableSegments;
        final int[] segmentToFeature;

        /**
         * Constructor of tile data
         * @param type  type of tile
         * @param numOfTiles  the number of the type in the stack
         * @param Shields  the number of shields in the tile
         * @param abbrevSegmentType  a string of abbreviated segment type
         * @param abbrevFeatureType  a string of abbreviated feature type
         * @param featureExtendableSegments  the number of extendable segments of the feature
         * @param segmentToFeature  the link from each segment to each feature, -1 when no link
         */
        TileData(String type,
                 int index,
                 int numOfTiles,
                 int Shields,
                 String abbrevSegmentType,
                 String abbrevFeatureType,
                 String featureExtendableSegments,
                 String segmentToFeature) {
            this.type = type;
            this.index = index;
            this.numOfTiles = numOfTiles;
            this.shields = Shields;
            this.featureExtendableSegments = stringArrToIntArr(featureExtendableSegments.split(" "));
            this.segmentToFeature = stringArrToIntArr(segmentToFeature.split(" "));

            String[] segmentTypeArr = abbrevSegmentType.split(" ");
            String[] featureTypeArr = abbrevFeatureType.split(" ");
            this.segmentType = new Segment.Type[segmentTypeArr.length];
            this.featureType = new Feature.Type[featureTypeArr.length];

            for (int i = 0; i < segmentTypeArr.length; i++) {
                switch (segmentTypeArr[i]) {
                    case "F": segmentType[i] = Segment.Type.FIELD; break;
                    case "R": segmentType[i] = Segment.Type.ROAD; break;
                    case "C": segmentType[i] = Segment.Type.CITY; break;
                }
            }
            for (int i = 0; i < featureTypeArr.length; i++) {
                switch (featureTypeArr[i]) {
                    case "r": featureType[i] = Feature.Type.ROAD; break;
                    case "c": featureType[i] = Feature.Type.CITY; break;
                    case "m": featureType[i] = Feature.Type.MONASTERY; break;
                }
            }
            assert(this.featureExtendableSegments.length == this.featureType.length);
            assert(this.segmentType.length == this.segmentToFeature.length);

        }
        private int[] stringArrToIntArr(String[] strArr) {
            int[] intArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                intArr[i] = Integer.parseInt(strArr[i]);
            }
            return intArr;
        }
    }

    LoadGameData() {
        startTileData = new TileData(
                "D", 3,1, 0,
                "R C R F",
                "r c",
                "2 1",
                "0 1 0 -1"
        );
        stackData.add(new TileData(
                "A", 0, 2, 0,
                "F F R F",
                "m r",
                "0 1",
                "-1 -1 1 -1"
        ));
        stackData.add(new TileData(
                "B", 1, 4, 0,
                "F F F F",
                "m",
                "0",
                "-1 -1 -1 -1"
        ));
        stackData.add(new TileData(
                "C", 2, 1, 1,
                "C C C C",
                "c",
                "4",
                "0 0 0 0"
        ));
        stackData.add(new TileData(
                "D", 3, 3, 0,
                "R C R F",
                "r c",
                "2 1",
                "0 1 0 -1"
        ));
        stackData.add(new TileData(
                "E", 4, 5, 0,
                "C F F F",
                "c",
                "1",
                "0 -1 -1 -1"
        ));
        stackData.add(new TileData(
                "F", 5, 2, 1,
                "F C F C",
                "c",
                "2",
                "-1 0 -1 0"
        ));
        stackData.add(new TileData(
                "G", 6, 1, 0,
                "C F C F",
                "c",
                "2",
                "0 -1 0 -1"
        ));
        stackData.add(new TileData(
                "H", 7, 3, 0,
                "F C F C",
                "c c",
                "1 1",
                "-1 0 -1 1"
        ));
        stackData.add(new TileData(
                "I", 8, 2, 0,
                "F C C F",
                "c c",
                "1 1",
                "-1 0 1 -1"
        ));
        stackData.add(new TileData(
                "J", 9, 3, 0,
                "C R R F",
                "c r",
                "1 2",
                "0 1 1 -1"
        ));
        stackData.add(new TileData(
                "K", 10, 3, 0,
                "R C F R",
                "c r",
                "1 2",
                "1 0 -1 1"
        ));
        stackData.add(new TileData(
                "L", 11,3, 0,
                "R C R R",
                "c r r r",
                "1 1 1 1",
                "1 0 2 3"
        ));
        stackData.add(new TileData(
                "M", 12, 2, 1,
                "C F F C",
                "c",
                "2",
                "0 -1 -1 0"
        ));
        stackData.add(new TileData(
                "N", 13, 3, 0,
                "C F F C",
                "c",
                "2",
                "0 -1 -1 0"
        ));
        stackData.add(new TileData(
                "O", 14, 2, 1,
                "C R R C",
                "c r",
                "2 2",
                "0 1 1 0"
        ));
        stackData.add(new TileData(
                "P", 15, 3, 0,
                "C R R C",
                "c r",
                "2 2",
                "0 1 1 0"
        ));
        stackData.add(new TileData(
                "Q", 16, 1, 1,
                "C C F C",
                "c",
                "3",
                "0 0 -1 0"
        ));
        stackData.add(new TileData(
                "R", 17, 3, 0,
                "C C F C",
                "c",
                "3",
                "0 0 -1 0"
        ));
        stackData.add(new TileData(
                "S", 18, 2, 1,
                "C C R C",
                "c r",
                "3 1",
                "0 0 1 0"
        ));
        stackData.add(new TileData(
                "T", 19, 1, 0,
                "C C R C",
                "c r",
                "3 1",
                "0 0 1 0"
        ));
        stackData.add(new TileData(
                "U", 20, 8, 0,
                "R F R F",
                "r",
                "2",
                "0 -1 0 -1"
        ));
        stackData.add(new TileData(
                "V", 21, 9, 0,
                "F F R R",
                "r",
                "2",
                "-1 -1 0 0"
        ));
        stackData.add(new TileData(
                "W", 22, 4, 0,
                "F R R R",
                "r r r",
                "1 1 1",
                "-1 0 1 2"
        ));
        stackData.add(new TileData(
                "X", 23, 1, 0,
                "R R R R",
                "r r r r",
                "1 1 1 1",
                "0 1 2 3"
        ));
    }
}