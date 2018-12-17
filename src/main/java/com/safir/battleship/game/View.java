package com.safir.battleship.game;

import com.safir.battleship.board.EnemyBoard;
import com.safir.battleship.board.PlayerBoard;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.ship.ShipType;

import java.util.List;

/**
 * @author safir
 */
public interface View {
    void viewBoards(PlayerBoard playerBoard, EnemyBoard enemyBoard);
    void welcomeUser();
    void viewInstructions();
    void announceGameOver(GameOverMessage message);
    void viewResultOfMove(Coordinates coordinates, CoordinatesState cellState);
    void viewResultOfEnemyMove(Coordinates coordinates, CoordinatesState cellState);
    void viewShipsLeftToPlace(List<ShipType> shipTypes);
}
