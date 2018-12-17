package com.safir.battleship.board;

import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;

/**
 * @author safir
 */
public interface EnemyBoard {
    void updateCellState(Coordinates coordinates, CoordinatesState newState);
    int getNumberOfShipsRemaining();
    boolean hasGuessedAlready(Coordinates coordinates);
    CoordinatesState getCellState(Coordinates coordinates);
    int getWidth();
    int getHeight();
}
