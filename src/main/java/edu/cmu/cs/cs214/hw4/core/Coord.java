package edu.cmu.cs.cs214.hw4.core;

/**
 * Coordinate class represent location on a two-dimensional surface using a pair of integers.
 * A coordinate can also represent direction (e.g. up as coord(-n,0); left as coord(0, -n), where
 * n is a positive integer, representing the step size in a given direction). A coordinate can add
 * another coordinate (or direction) similar to 2D vector addition. A coordinate can also be
 * inverted (i.e. its components are multiplied by -1).
 *
 * @author Jiawei Mai
 */
public class Coord {
    private int x, y;

    /**
     * Constructor for the Coord class
     * @param x  x coordinate
     * @param y  y coordinate
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a new coordinates as the result of adding the coordinate
     * itself to the parameter c
     * @param c  another coordinate
     * @return  new coordinate results from the addition
     */
    public Coord add(Coord c) {
        return new Coord(this.x + c.x, this.y + c.y);
    }

    /**
     * Returns a new coordinate as the result of the reversion
     * @return  a new reversed coordinate
     */
    public Coord reverse() {
        return new Coord(this.x*-1, this.y*-1);
    }

    /**
     * Override Object equals method
     * @param o  Object to compare against
     * @return boolean as the result of comparison
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord)) { return false; }
        Coord c = (Coord) o;
        return (x == c.x) && (y == c.y);
    }

    /**
     * Override Object hashCode method
     * @return hash code of the Coordinate
     */
    @Override
    public int hashCode() {
        int PRIME = 31;
        int hash = 1;
        hash = PRIME * hash + x;
        hash = PRIME * hash + y;
        return hash;
    }

    /**
     * String representation (i, j) of Coordinates
     * @return representation string
     */
    @Override
    public String toString() {
        return (String.format("(%d, %d)", x, y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
