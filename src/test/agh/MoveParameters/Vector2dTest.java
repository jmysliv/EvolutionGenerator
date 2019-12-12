package agh.MoveParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2dTest {

    private Vector2d v1 = new Vector2d(1, 1);
    private Vector2d v2 = new Vector2d(0, 0);

    @Test
    public void precedes() {
        assertTrue( v1.precedes(new Vector2d(1, 1)));
        assertTrue( v1.precedes(new Vector2d(2, 2)));
        assertFalse(v1.precedes(new Vector2d(1, 0)));
        assertFalse(v1.precedes(new Vector2d(0, 1)));
    }

    @Test
    public void follows() {
        assertTrue( v1.follows(new Vector2d(1, 1)));
        assertTrue( v1.follows(new Vector2d(0, 0)));
        assertFalse(v1.follows(new Vector2d(1, 2)));
        assertFalse(v1.follows(new Vector2d(2, 1)));
    }

    @Test
    public void upperRight() {
        assertEquals( new Vector2d(2, 1), v1.upperRight(new Vector2d(2, 0)));
        assertEquals(v1, v2.upperRight(v1));
    }

    @Test
    public void lowerLeft() {
        assertEquals( new Vector2d(1, 0), v1.lowerLeft(new Vector2d(2, 0)));
        assertEquals(v2, v2.lowerLeft(v1));
    }

    @Test
    public void add() {
        assertEquals( new Vector2d(2, 2), v1.add(v1));
        assertEquals(v1, v2.add(v1));
    }

    @Test
    public void substract() {
        assertEquals(new Vector2d(0, 0), v1.substract(v1));
        assertEquals( new Vector2d(-1, -1), v2.substract(v1));
    }

    @Test
    public void testEquals() {
        assertTrue( v1.equals(new Vector2d(1, 1)));
        assertFalse(v1.equals(new Vector2d(1, 2)));
        assertFalse(v1.equals(new Vector2d(2, 1)));
    }

    @Test
    public void oposite() {
        assertEquals( new Vector2d(-1, -1), v1.oposite());
    }
}