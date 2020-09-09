package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

/**
 * Board class representing the game board in Carcassonne. The board is responsible for checking
 * validity of tile placements. It also checks if action are available given a tile and the current board
 * configuration. If a tile placement is valid, the board adds a new tile at its location and updates and
 * scores all features triggered by the new tile placement.
 *
 * @author Vincent Mai
 */
class Board {
    private Map<Coord, Tile> tiles = new HashMap<>();
    private List<Feature> features = new ArrayList<>();
    private static final Coord[] Direction = {
            new Coord(-1,0),
            new Coord(0,1),
            new Coord(1,0),
            new Coord(0,-1)
    };
    static final Coord BOARD_ORIGIN = new Coord(0,0);

    /**
     * Constructor of the board, adds the start tile during instantiation.
     * @param start_tile  designated start tile.
     */
    Board(Tile start_tile) {
        tiles.put(start_tile.getLocation(), start_tile);
        addTileFeatures(start_tile);
    }

    /**
     * Validates the placement of a tile
     * @param t  tile to place
     * @return  boolean representing placement validity
     */
    boolean validTilePlacement(Tile t) {
        return (getPlacementLocs().contains(t.getLocation()))
                && (validJointSegments(t)); }

    /**
     * Checks if current tile draw yields actions
     * @param t  tile currently drawn
     * @return  boolean  of whether actions are available
     */
    boolean actionAvailable(Tile t) {
        for (Coord loc : getPlacementLocs()) {
            for (int i = 0; i < 4; i++) {
                t.rotate();
                try {
                    t.place(loc, this);
                    t.undoPlace();
                    t.undoAllRotate();
                    return true;
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        }
        return false;
    }

    /**
     * Adds a new tile (its location and features) to the existing board.
     * Calls {@link Board#mergeFeatures(Tile)} to merge tile features that links to board features.
     * @param t  tile to add
     */
    void add(Tile t) {
        tiles.put(t.getLocation(), t);
        mergeFeatures(t);
        addTileFeatures(t);
    }

    List<Feature> scoreCompletedFeatures() {
        List<Feature> scoredFeatures = new ArrayList<>();
        for (Feature f: features) {
            if (f.completed(this)) {
                f.score(this);
                System.out.println("scoring "+f);
                scoredFeatures.add(f);
            }
        }
        features.removeAll(scoredFeatures);
        return scoredFeatures;
    }

    void scoreFinalFeatures() {
        for (Feature f: features) {
            f.score(this);
        }
    }

    Set<Coord> getTilesLoc() {
        return tiles.keySet();
    }

    /**
     * Merge all features on a newly placed tile with the existing ones on the board. Merge
     * new features to existing ones if they are connected. Once merged, new calls on features
     * returns the existing ones.
     *
     * @param t  newly placed tile
     */
    private void mergeFeatures(Tile t) {
        for (Coord dir : Direction) {
            Coord neighborLoc = t.getLocation().add(dir);
            if (!tiles.containsKey(neighborLoc)) { continue; }
            Feature f = t.getSegment(dir).getLinkedFeature();
            if (f == null) { continue; }
            Segment neighborSegment = tiles.get(neighborLoc).getSegment(dir.reverse());
            Feature existingFeature = neighborSegment.getLinkedFeature();
            // (feature not null) from a feature get all its tiles
            for (Tile tileOfFeature : f.getTiles()) {
                // from each tile get all its linked segments
                List<Segment> linkedSegments = tileOfFeature.getLinkedSegments(f);
                // from each segment link itself to the existing feature  (rerouting)
                for (Segment s : linkedSegments) { s.linkFeature(existingFeature); }
                // existingFeature.merge(feature): +tiles, +extendibles-2, +meeples
                existingFeature.merge(f);
                // remove the feature if it exists in board.features  (consolidating)
                this.features.remove(f);
                // in each tile, link its feature to existing feature  (rerouting)
                tileOfFeature.replaceFeature(f, existingFeature);
            }
        }
    }

    private void addTileFeatures(Tile t) {
        for (Feature f : t.getFeatures()) {
            if ((f != null) && (!this.features.contains(f))) {
                this.features.add(f);
            }
        }
    }

    private Boolean validJointSegments(Tile t) {
        for (Coord dir: Direction) {
            Coord neighborLoc = t.getLocation().add(dir);
            if (!tiles.containsKey(neighborLoc)) {
                continue;
            }
            Segment s0 = t.getSegment(dir);
            Segment s1 = tiles.get(neighborLoc).getSegment(dir.reverse());
            if (!s0.equals(s1)) { return false; }
        }
        return true;
    }

    /**
     * Returns a set of joint features given a feature on the newly placed tile
     * this method can only be applied to features on newly placed tiles before
     * they merge with existing features on the board
     * @param f  feature on a new tile to evaluate on
     * @return  jointFeatures   a list of existing features which joints the feature f
     */
    List <Feature> findJointExistingFeatures(Feature f){
        List<Feature> jointFeatures = new ArrayList<>();
        assert(f.getTiles().size() == 1);  // features on a new tile have yet to be connected
        Tile t = f.getTiles().get(0);
        List<Segment> linkedSegments = t.getLinkedSegments(f);
        for (Coord dir : Direction) {
            Coord neighborLoc = t.getLocation().add(dir);
            if (!tiles.containsKey(neighborLoc)) { continue; }
            if (linkedSegments.contains(t.getSegment(dir)) &&
                t.getSegment(dir).getLinkedFeature().equals(f)) {
                // Temporary fix. Because segments is hashed on type,
                // linked segments always contain same segment type
                Segment neighborSegment = tiles.get(neighborLoc).getSegment(dir.reverse());
                jointFeatures.add(neighborSegment.getLinkedFeature());
            }
        }
        return jointFeatures;
    }

    /**
     * Return a list of legal placement locations given the current board
     * where at least one neighbor exist around each location
     * @return  legalPlacement  the legal placement locations
     */
    List<Coord> getPlacementLocs() {
        List<Coord> legalPlacement = new ArrayList<>();
        for (Coord loc: tiles.keySet()) {
            for (Coord dir: Direction) {
                Coord next_loc = loc.add(dir);
                if (!tiles.containsKey(next_loc) && !legalPlacement.contains(next_loc)) {
                    legalPlacement.add(next_loc);
                }
            }
        }
        return legalPlacement;
    }
}
