package com.safir.battleship.player;

import com.safir.battleship.board.EnemyBoard;
import com.safir.battleship.board.EnemyBoardImpl;
import com.safir.battleship.board.PlayerBoard;
import com.safir.battleship.board.PlayerBoardImpl;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;

/**
 * @author safir
 */
public abstract class AbstractPlayer implements Player {
    protected final PlayerBoard playerBoard;
    protected final EnemyBoard enemyBoard;

    public AbstractPlayer(int newBoardWidth, int newBoardHeight, int newShipsRemaining) {
        playerBoard = new PlayerBoardImpl (newBoardWidth, newBoardHeight);
        enemyBoard = new EnemyBoardImpl (newBoardWidth, newBoardHeight, newShipsRemaining);
    }

    @Override
    public CoordinatesState takeHit(Coordinates coordinates) {
        return playerBoard.takeHit(coordinates);
    }

    @Override
    public boolean hasLost() {
        return playerBoard.areAllShipsSunk();
    }

    @Override
    public boolean hasAlreadyGuessed(Coordinates coordinates) {
        return playerBoard.hasGuessedAlready(coordinates);
    }

    @Override
    public void viewState() {
        getView().viewBoards(playerBoard, enemyBoard);
    }

    @Override
    public void updateEnemyBoard(Coordinates coordinates, CoordinatesState cellState) {
        enemyBoard.updateCellState(coordinates, cellState);
    }
}
