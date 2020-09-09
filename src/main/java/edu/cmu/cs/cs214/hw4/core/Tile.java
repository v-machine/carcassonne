package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tile class represents tiles in Carcassonne. An instance of a tile stores its location and orientation,
 * the segments it contains and the features in the file. A tile can be rotated by rotate() method and
 * placed on the board with place() method. The game automatically discards a file if it cannot be placed.
 *
 * @author Vincent Mai
 */
class Tile {
    private String type;
    private int index;
    private Coord location;
    private List<Segment> segments = new ArrayList<>();
    private List<Coord> orientation = new ArrayList<>(Arrays.asList(SEGMENT_LOCATIONS));
    private List<Feature> features = new ArrayList<>();
    private static final Coord[] SEGMENT_LOCATIONS = {
            new Coord(-1,0),
            new Coord(0,1),
            new Coord(1,0),
            new Coord(0,-1)
    };

    /**
     * Constructs a tile object by data of the tile
     * @param data  contains data of the tile to be constructed
     */
    Tile(LoadGameData.TileData data) {
        this.type = data.type;
        this.index = data.index;
        int shields = data.shields;
        Segment.Type[] segmentType = data.segmentType;
        Feature.Type[] featureType = data.featureType;
        int[] featureExtendableSegments = data.featureExtendableSegments;
        int[] segmentToFeature = data.segmentToFeature;
        for (Segment.Type s : segmentType) {
            this.segments.add(new Segment(s));
        }
        for (int i = 0; i < featureType.length; i++) {
            Feature.Type f = featureType[i];
            int numExtendableSegments = featureExtendableSegments[i];
            switch (f) {
                case ROAD: this.features.add(new RoadFeature(f, this, numExtendableSegments)); break;
                case CITY: this.features.add(new CityFeature(f, this, numExtendableSegments, shields)); break;
                case MONASTERY: this.features.add(new MonasteryFeature(f, this)); break;
            }
        }
        for (int i=0; i<segmentToFeature.length; i++){
            if (segmentToFeature[i] != -1) {
                segments.get(i).linkFeature(features.get(segmentToFeature[i]));
            }
        }
    }

    /**
     * Overloaded tile constructor for start tile
     * @param data  data of each tile loaded from LoadGameData
     * @param origin  Coordinate (0,0) on the board
     */
    Tile(LoadGameData.TileData data, Coord origin) {
        this(data);
        assert(origin.equals(Board.BOARD_ORIGIN));
        this.location = origin;
    }

    /**
     * Returns a set of features in the current tile
     * @return  a list of features
     */
    List<Feature> getFeatures() { return features; }

    /**
     * Replaces a feature f0 in a tile with another feature f1
     * @param f0  feature to be replaced
     * @param f1  replacement feature
     */
    void replaceFeature(Feature f0, Feature f1) {
        features.set(features.indexOf(f0), f1);
    }

    /**
     * Rotates a tile by 90 degree (CW)
     */
    void rotate() {
        orientation.add(orientation.remove(0));
    }

    /**
     * Undo all tile rotations, restores its initial orientation
     */
    void undoAllRotate() {
        orientation = new ArrayList<>(Arrays.asList(SEGMENT_LOCATIONS));
    }

    /**
     * Places a tile on the board, if placement is valid, otherwise throws exception
     * @param location  where tile is placed
     * @param board  the board of the game
     *
     * @throws IllegalArgumentException  if placement is invalid
     */
    void place(Coord location, Board board) {
        this.location = location;
        if (!board.validTilePlacement(this)) {
            this.location = null;
            throw new IllegalArgumentException("Invalid tile placement!");
        }
    }

    /**
     * Undo placement of a tile
     */
    void undoPlace() {
        if (!this.location.equals(Board.BOARD_ORIGIN)) {
            this.location = null;
        }
    }

    /**
     * Returns the location (Coord) of the tile
     * @return location  coordinates of the tile
     */
    Coord getLocation() {
        return location;
    }

    /**
     * Returns the segment on a tile given a segment location
     * @param segmentLoc  the location of the segment
     * @return segment  on the given segment location
     */
    Segment getSegment(Coord segmentLoc) {
        return segments.get(orientation.indexOf(segmentLoc));
    }

    /**
     * Returns a list of segments, on a tile, that link to the same feature.
     * Returns an empty list if a feature has no segments.
     * @param f  common features the tile is linked to
     * @return linked segments
     */
    List<Segment> getLinkedSegments(Feature f) {
        List<Segment> linkedSegments = new ArrayList<>();
        for (Segment s: segments) {
            if (f.equals(s.getLinkedFeature())) { linkedSegments.add(s); }
        }
        return linkedSegments;
    }

    /**
     * Override toString methods
     * @return string representation of tile
     */
    @Override
    public String toString() {
        StringBuilder tileRep = new StringBuilder();
        StringBuilder segmentStr = new StringBuilder();
        StringBuilder featureStr = new StringBuilder();
        for (Coord c : SEGMENT_LOCATIONS) {
            segmentStr.append(getSegment(c));
            segmentStr.append(c).append(" ");
        }
        for (Feature f : features) {
            featureStr.append(f).append(" ");
        }
        tileRep.append("Tile: ").append(type);
        if (location == null) {
            tileRep.append(" (in stack)");
        } else {
            tileRep.append(" on ").append(location);
        }
        tileRep.append(" with segments: ");
        tileRep.append(segmentStr);
        tileRep.append(" with features: ");
        tileRep.append(featureStr);
        return tileRep.toString();
    }

    /**
     * returns the index of the current tile
     *
     * @return  index of the tile
     */
    public int getIndex() {
        return index;
    }
}