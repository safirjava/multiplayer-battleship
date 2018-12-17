package com.safir.battleship.board;

import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.grid.Orientation;
import com.safir.battleship.ship.Ship;
import com.safir.battleship.ship.ShipImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerBoardTest {
    private PlayerBoard playerBoard;
    private Ship ship;

    @Before
    public void setUp() {
        playerBoard = new PlayerBoardImpl(7, 7);
        ship = new ShipImpl (3, new Coordinates (0, 0), Orientation.HORIZONTAL);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructIncorrect() {
        new PlayerBoardImpl(0, 5);
    }

    @Test
    public void testGetWidth() {
        assertEquals(playerBoard.getWidth(), 7);
    }

    @Test
    public void testGetHeight() {
        assertEquals(playerBoard.getHeight(), 7);
    }

    @Test
    public void testPlaceShip() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        assertEquals(playerBoard.getShip(new Coordinates(0,0)), ship);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPlaceNullShip() throws ShipOverlapException {
        playerBoard.placeShip(null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testPlaceShipOutsideBounds() throws ShipOverlapException {
        Ship ship2 = new ShipImpl(3, new Coordinates(10, 10), Orientation.HORIZONTAL);
        playerBoard.placeShip(ship2);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testPlaceShipLengthOutsideBounds() throws ShipOverlapException {
        Ship ship2 = new ShipImpl(3, new Coordinates(7, 7), Orientation.VERTICAL);
        playerBoard.placeShip(ship2);
    }

    @Test (expected = ShipOverlapException.class)
    public void testPlaceShipsOverlap() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        Ship ship2 = new ShipImpl(3, new Coordinates(1, 0), Orientation.VERTICAL);
        playerBoard.placeShip(ship2);
    }

    @Test (expected = ShipOverlapException.class)
    public void testPlaceShipsOverlap2() throws ShipOverlapException {
        playerBoard.placeShip(new ShipImpl(3, new Coordinates(2, 0), Orientation.VERTICAL));
        playerBoard.placeShip(new ShipImpl(3, new Coordinates(0, 2), Orientation.HORIZONTAL));
    }

    @Test (expected = ShipOverlapException.class)
    public void testPlaceShipsOverlapTest3() throws ShipOverlapException {
        playerBoard.placeShip(new ShipImpl(5, new Coordinates(2, 0), Orientation.VERTICAL));
        playerBoard.placeShip(new ShipImpl(4, new Coordinates(0, 3), Orientation.HORIZONTAL));
    }

    @Test (expected = ShipOverlapException.class)
    public void testPlaceShipsOverlapTest4() throws ShipOverlapException {
        playerBoard.placeShip(new ShipImpl(3, new Coordinates(2, 0), Orientation.VERTICAL));
        playerBoard.placeShip(new ShipImpl(3, new Coordinates(2, 2), Orientation.VERTICAL));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testPlaceShipLongerThanGridOutsideBounds() throws ShipOverlapException {
        playerBoard.placeShip(new ShipImpl(8, new Coordinates(2, 0), Orientation.VERTICAL));
    }

    @Test
    public void testGetCellStateNothing() {
        assertEquals(playerBoard.getCellState(new Coordinates(0,0)), CoordinatesState.NOTHING);
    }

    @Test
    public void testGetCellStateMiss() {
        playerBoard.takeHit(new Coordinates(0,0));
        assertEquals(playerBoard.getCellState(new Coordinates(0,0)),CoordinatesState.MISS);
    }

    @Test
    public void testGetCellStateHit() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        playerBoard.takeHit(new Coordinates(0,0));
        assertEquals(playerBoard.getCellState(new Coordinates(0,0)),CoordinatesState.SHIP_HIT);
    }

    @Test
    public void testGetCellStateSunk() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        playerBoard.takeHit(new Coordinates(0,0));
        playerBoard.takeHit(new Coordinates(1,0));
        playerBoard.takeHit(new Coordinates(2,0));
        assertEquals(playerBoard.getCellState(new Coordinates(2,0)),CoordinatesState.SHIP_SUNK);
    }

    @Test
    public void testHasGuessedAlready() {
        playerBoard.takeHit(new Coordinates(0,0));
        assertTrue(playerBoard.hasGuessedAlready(new Coordinates(0,0)));
    }

    @Test
    public void testShipsRemaining() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        assertEquals(playerBoard.getNumberOfShipsRemaining(),1);
    }

    @Test
    public void testGetShips() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        assertEquals(playerBoard.getShips().get(0),ship);
    }

    @Test
    public void testGetShipsUnmodifiable() throws ShipOverlapException {
        // Testing both cases for either any exception that could be thrown from mutating list
        // or mutating the list doesn't throw an exception but it doesn't mutate the list returned
        // from the method getShips().
        playerBoard.placeShip(ship);
        try {
            List<Ship> ships = playerBoard.getShips();
            ships.clear();
            assertEquals(playerBoard.getShips().size(), 1);
        } catch(Exception ignored) {}
    }

    @Test
    public void testNotAllShipsSunk() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        assertFalse(playerBoard.areAllShipsSunk());
    }

    @Test
    public void testAllShipsAreSunk() throws ShipOverlapException {
        playerBoard.placeShip(ship);
        playerBoard.takeHit(new Coordinates(0,0));
        playerBoard.takeHit(new Coordinates(1,0));
        playerBoard.takeHit(new Coordinates(2,0));
        assertTrue(playerBoard.areAllShipsSunk());
    }
}
