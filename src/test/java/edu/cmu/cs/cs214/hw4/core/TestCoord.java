package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for {@link Coord}
 *
 * @author Vincent Mai
 */
public class TestCoord {
    Coord c1 = new Coord(1,0);
    Coord c2 = new Coord(-1,-1);
    Coord c3 = new Coord(2,3);

    /**
     * test {@link Coord#equals(Object)}
     */
    @Test
    public void testEquals() {
        assertEquals(c1, c1);
        assertEquals(c2, new Coord(-1,-1));
        assertNotEquals(c3, c1);
        assertNotEquals(c3, "notACoordinate");
    }

    /**
     * test {@link Coord#add(Coord)}
     */
    @Test
    public void testAdd() {
        assertEquals(c1.add(c2), new Coord(0, -1));
        assertEquals(c1.add(c3), new Coord(3, 3));
    }

    /**
     * test {@link Coord#invert(Coord)}
     */
    @Test
    public void testInvert() {
        assertEquals(c1.reverse(), new Coord(-1, 0));
        assertEquals(c2.reverse(), new Coord(1, 1));
        assertEquals(c3.reverse(), new Coord(-2, -3));
    }
}
