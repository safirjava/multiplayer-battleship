package com.safir.battleship.grid;

import com.safir.battleship.Exception.MalformattedException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinatesTest {

    @Test
    public void testCheckInBounds() {
        Coordinates coordinates = new Coordinates(1,1);
        coordinates.checkBounds(3, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeBounds() {
        Coordinates coordinates = new Coordinates(-1, 0);
        coordinates.checkBounds(3, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsX() {
        Coordinates coordinates = new Coordinates(4,0);
        coordinates.checkBounds(3, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsY() {
        Coordinates coordinates = new Coordinates(0,4);
        coordinates.checkBounds(3, 3);
    }

    @Test
    public void testStringToCoordinates() throws MalformattedException {
        Coordinates coordinates = new Coordinates("A1");
        assertEquals(coordinates.x, 0);
        assertEquals(coordinates.y, 0);
    }

    @Test(expected = MalformattedException.class)
    public void testTwoLetters() throws MalformattedException {
        Coordinates coordinates = new Coordinates("aA");
    }

    @Test(expected = MalformattedException.class)
    public void testTwoNumbers() throws MalformattedException {
        Coordinates coordinates = new Coordinates("11");
    }

    @Test(expected = MalformattedException.class)
    public void testNullString() throws MalformattedException {
        Coordinates coordinates = new Coordinates("");
    }

    @Test
    public void testToString() {
        Coordinates coordinates = new Coordinates(4,0);
        assertEquals(coordinates.toString(), "E1");
    }
}
