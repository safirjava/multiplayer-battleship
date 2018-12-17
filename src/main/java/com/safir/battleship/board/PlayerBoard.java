package com.safir.battleship.board;

import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.ship.Ship;

import java.util.List;

/**
 * @author safir
 */
public interface PlayerBoard {
    int getWidth();
    int getHeight();
    void placeShip(Ship ship) throws ShipOverlapException;
    CoordinatesState getCellState(Coordinates coordinates);
    boolean hasGuessedAlready(Coordinates coordinates);
    Ship getShip(Coordinates coordinates);
    int getNumberOfShipsRemaining();
    List<Ship> getShips();
    boolean areAllShipsSunk();
    CoordinatesState takeHit(Coordinates coordinates);
}
