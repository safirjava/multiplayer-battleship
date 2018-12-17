package com.safir.battleship.ship;

import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.Orientation;

/**
 * @author safir
 */
public interface Ship {
    int getLength();
    boolean isAt(Coordinates coordinates);
    void takeAHit();
    boolean isSunk();
    Coordinates getStartingPosition();
    Coordinates getEndPosition();
    Orientation getOrientation();
    Coordinates[] getAllCoordinates();
}
