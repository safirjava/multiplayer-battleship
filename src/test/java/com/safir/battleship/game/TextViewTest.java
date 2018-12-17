package com.safir.battleship.game;

import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.board.EnemyBoard;
import com.safir.battleship.board.EnemyBoardImpl;
import com.safir.battleship.board.PlayerBoard;
import com.safir.battleship.board.PlayerBoardImpl;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.grid.Orientation;
import com.safir.battleship.ship.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TextViewTest {
    private PlayerBoard playerBoard;
    private EnemyBoard enemyBoard;
    private Ship smallShip, mediumShip, largeShip;
    private TextView view;

    @Before
    public void setup() throws ShipOverlapException {
        //Boards
        playerBoard = new PlayerBoardImpl (17,12);
        enemyBoard = new EnemyBoardImpl (17,12, 5);
        // Ships
        smallShip = new ShipImpl (2, new Coordinates (0,0), Orientation.VERTICAL);
        mediumShip = new ShipImpl(3, new Coordinates(2,1), Orientation.VERTICAL);
        largeShip = new ShipImpl(4, new Coordinates(0,6), Orientation.HORIZONTAL);
        // Place ships onto player board
        playerBoard.placeShip(smallShip);
        playerBoard.placeShip(mediumShip);
        playerBoard.placeShip(largeShip);
        // Make a hit on 2 ships
        playerBoard.takeHit(new Coordinates(0,1));
        playerBoard.takeHit(new Coordinates(0,0));
        playerBoard.takeHit(new Coordinates(1,6));
        playerBoard.takeHit(new Coordinates(5,5));
        // Try to hit enemy
        enemyBoard.updateCellState(new Coordinates(0,3), CoordinatesState.MISS);
        enemyBoard.updateCellState(new Coordinates(1,4), CoordinatesState.MISS);
        enemyBoard.updateCellState(new Coordinates(0,0), CoordinatesState.MISS);
        enemyBoard.updateCellState(new Coordinates(6,6), CoordinatesState.SHIP_HIT);
        enemyBoard.updateCellState(new Coordinates(5,6), CoordinatesState.SHIP_HIT);
        enemyBoard.updateCellState(new Coordinates(4,6), CoordinatesState.SHIP_HIT);

        view = new TextView();
    }

    @Test
    public void testViewBoards() {
        view.viewBoards(playerBoard, enemyBoard);
    }

    @Test
    public void testWelcomeMessage() {
        view.welcomeUser();
    }

    @Test
    public void testAnnounceGameOverWin() {
        view.announceGameOver(GameOverMessage.YOU_WON);
    }

    @Test
    public void testAnnounceGameOverLost() {
        view.announceGameOver(GameOverMessage.YOU_LOST);
    }

    @Test
    public void testShipsLeftToPlace() {
        List<ShipType> ships = new ArrayList<> ();
        ships.add(new AircraftCarrierShip ());
        ships.add(new SubmarineShip ());
        ships.add(new BattleshipShip ());
        ships.add(new DestroyerShip ());
        view.viewShipsLeftToPlace(ships);
    }
}
