package com.safir.battleship.board;

import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EnemyBoardTest {
    private EnemyBoard board;

    @Before
    public void setUp(){
        board = new EnemyBoardImpl(7, 7, 5);
    }

    @Test
    public void testUpdateCellState(){
        board.updateCellState(new Coordinates (1, 1), CoordinatesState.MISS);
        assertEquals(board.getCellState(new Coordinates(1, 1)), CoordinatesState.MISS);

    }

    @Test
    public void testGetNumberOfShipsRemaining() {
        assertEquals(board.getNumberOfShipsRemaining(), 5);
    }

    @Test
    public void testHasNotGuessed(){
        assertFalse(board.hasGuessedAlready(new Coordinates(2,2)));
    }

    @Test
    public void testHasGuessed(){
        board.updateCellState(new Coordinates(3,3), CoordinatesState.MISS);
        assertTrue(board.hasGuessedAlready(new Coordinates(3,3)));
    }

    @Test
    public void testGetCellState(){
        assertEquals(board.getCellState(new Coordinates(1,1)), CoordinatesState.NOTHING);
    }

    @Test
    public void testGetWidth(){
        assertEquals(board.getWidth(), 7);
    }

    @Test
    public void testGetHeight(){
        assertEquals(board.getHeight(), 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetCellStateOutOfBounds() {
        board.getCellState(new Coordinates(7,5));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdateCellStateOutOfBounds() {
        board.updateCellState(new Coordinates(6,8), CoordinatesState.MISS);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHasGuessedOutOfBounds() {
        board.hasGuessedAlready(new Coordinates(-1,3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCellStateNull() {
        board.updateCellState(new Coordinates(3,3), null);
    }
}
