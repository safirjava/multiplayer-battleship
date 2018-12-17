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
public class ComputerView implements View {
    @Override
    public void viewBoards(PlayerBoard playerBoard, EnemyBoard enemyBoard) {

    }

    @Override
    public void welcomeUser() {

    }

    @Override
    public void viewInstructions() {

    }

    @Override
    public void announceGameOver(GameOverMessage message) {

    }

    @Override
    public void viewResultOfMove(Coordinates coordinates, CoordinatesState cellState) {

    }

    @Override
    public void viewResultOfEnemyMove(Coordinates coordinates, CoordinatesState cellState) {

    }

    @Override
    public void viewShipsLeftToPlace(List<ShipType> shipTypes) {

    }
}
