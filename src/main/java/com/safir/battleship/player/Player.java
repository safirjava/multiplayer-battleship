package com.safir.battleship.player;

import com.safir.battleship.Exception.QuitException;
import com.safir.battleship.Exception.ResignException;
import com.safir.battleship.game.View;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.ship.ShipType;

/**
 * @author safir
 */
public interface Player {
    void placeShipOnToPlayerBoard(int lengthOfShip) throws ResignException;
    Coordinates chooseMove() throws QuitException;
    CoordinatesState takeHit(Coordinates coordinates) throws QuitException;
    boolean hasLost();
    boolean hasAlreadyGuessed(Coordinates coordinates);
    void viewState();
    void updateEnemyBoard(Coordinates coordinates, CoordinatesState cellState);
    View getView();
}
