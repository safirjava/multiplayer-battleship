package com.safir.battleship.board;

import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.ship.Ship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author safir
 */
public class PlayerBoardImpl implements PlayerBoard {
    private final int width, height;
    private final CoordinatesState[][] grid;
    private final List<Ship> ships;

    public PlayerBoardImpl(int newWidth, int newHeight) {
        if(newWidth <= 0 || newHeight <= 0){
            throw new IllegalArgumentException("You cannot make a grid size of " + newWidth + " by " + newHeight );
        }
        width = newWidth;
        height = newHeight;
        grid = new CoordinatesState[newWidth][newHeight];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                grid[x][y] = CoordinatesState.NOTHING;
            }
        }
        ships = new ArrayList<> ();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void placeShip(Ship ship) throws ShipOverlapException {
        if(ship == null) {
            throw new IllegalArgumentException("Tried to place a null ship.");
        }

        // test if new ship will be out of bounds
        // we don't need to check startingPosition is in bounds,
        // because Ship constructor guarantees its coordinates are non-negative
        ship.getEndPosition().checkBounds(width, height);

        // test if new ship overlaps any previous ship
        for(Coordinates c : ship.getAllCoordinates()) {
            if(getCellState(c) != CoordinatesState.NOTHING){
                throw new ShipOverlapException();
            }
        }

        // otherwise,
        // update the cell states for the new ship
        for(Coordinates c : ship.getAllCoordinates()) {
            grid[c.x][c.y] = CoordinatesState.SHIP_NOT_HIT;
        }

        // add the ship to the list of ships
        ships.add(ship);
    }

    @Override
    public CoordinatesState getCellState(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        return grid[coordinates.x][coordinates.y];
    }

    @Override
    public boolean hasGuessedAlready(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        // a ship not hit is not yet guessed
        return getCellState(coordinates) != CoordinatesState.NOTHING
                && getCellState(coordinates) != CoordinatesState.SHIP_NOT_HIT;
    }

    @Override
    public Ship getShip(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        for(Ship ship : ships) {
            if(ship.isAt(coordinates)) {
                return ship;
            }
        }
        return null;
    }

    @Override
    public int getNumberOfShipsRemaining() {
        int counter = 0;
        for(Ship ship : ships){
            if(!ship.isSunk()) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public List<Ship> getShips() {
        return Collections.unmodifiableList(ships);
    }

    @Override
    public boolean areAllShipsSunk() {
        return getNumberOfShipsRemaining() == 0;
    }

    @Override
    public CoordinatesState takeHit(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        // check they haven't already hit here
        if(hasGuessedAlready(coordinates) ){
            throw new IllegalArgumentException("You have already guessed " + coordinates);
        }

        // find the ship, if it exists, make it take a hit.
        Ship ship = getShip(coordinates);
        if(ship != null){
            ship.takeAHit();

            if(ship.isSunk()) {
                for(Coordinates c : ship.getAllCoordinates()) {
                    grid[c.x][c.y] = CoordinatesState.SHIP_SUNK;
                }
            } else {
                grid[coordinates.x][coordinates.y] = CoordinatesState.SHIP_HIT;
            }
        } else {
            grid[coordinates.x][coordinates.y] = CoordinatesState.MISS;
        }
        return grid[coordinates.x][coordinates.y];
    }
}
