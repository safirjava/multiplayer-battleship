package com.safir.battleship.board;

import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;

/**
 * @author safir
 */
public class EnemyBoardImpl implements EnemyBoard {
    private final CoordinatesState[][] grid;
    private final int width, height;
    private int shipsRemaining;

    public EnemyBoardImpl(int newWidth, int newHeight, int newShipsRemaining) {
        if(newWidth <= 0 || newHeight <=0) {
            throw new IllegalArgumentException(newHeight + " by " + newWidth + " is not a legal board size");
        } else if(newShipsRemaining <= 0) {
            throw new IllegalArgumentException("Can not start the game as the number of ships remaining is " + newShipsRemaining);
        }

        width = newWidth;
        height = newHeight;
        shipsRemaining = newShipsRemaining;

        grid = new CoordinatesState[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = CoordinatesState.NOTHING;
            }
        }
    }

    @Override
    public void updateCellState(Coordinates coordinates, CoordinatesState newState) {
        coordinates.checkBounds(width, height);
        if(newState == null) {
            throw new IllegalArgumentException("New cell state was null.");
        } else if(newState == CoordinatesState.SHIP_NOT_HIT) {
            throw new IllegalArgumentException(newState + " is not a valid cell state for enemy board.");
        } else if(newState == CoordinatesState.SHIP_SUNK) {
            shipsRemaining --;
            grid[coordinates.x][coordinates.y] = CoordinatesState.SHIP_HIT;
        } else {
            grid[coordinates.x][coordinates.y] = newState;
        }
    }

    @Override
    public int getNumberOfShipsRemaining() {
        return shipsRemaining;
    }

    @Override
    public boolean hasGuessedAlready(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        return grid[coordinates.x][coordinates.y] != CoordinatesState.NOTHING;
    }

    @Override
    public CoordinatesState getCellState(Coordinates coordinates) {
        coordinates.checkBounds(width, height);
        return grid[coordinates.x][coordinates.y];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
