package com.safir.battleship.player;

import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.game.ComputerView;
import com.safir.battleship.game.View;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.Orientation;
import com.safir.battleship.ship.Ship;
import com.safir.battleship.ship.ShipImpl;

import java.util.Random;

/**
 * @author safir
 */
public class ComputerPlayer extends AbstractPlayer {
    private final Random random;

    public ComputerPlayer(int newBoardWidth, int newBoardHeight, int newShipsRemaining) {
        super(newBoardWidth, newBoardHeight, newShipsRemaining);
        random = new Random();
    }

    @Override
    public void placeShipOnToPlayerBoard(int lengthOfShip) {
        while (true) {
            try {
                // generate random coordinates
                int x = random.nextInt(playerBoard.getWidth());
                int y = random.nextInt(playerBoard.getHeight());
                Coordinates coordinates = new Coordinates(x, y);
                coordinates.checkBounds(playerBoard.getWidth(), playerBoard.getHeight());

                // generate random orientation where true is right and false is down
                Ship ship = null;
                if(random.nextBoolean()) {
                    ship = new ShipImpl (lengthOfShip, coordinates, Orientation.HORIZONTAL);
                } else {
                    ship = new ShipImpl(lengthOfShip, coordinates, Orientation.VERTICAL);
                }

                playerBoard.placeShip(ship);
                break;
            } catch(IndexOutOfBoundsException ignored) {
            } catch(ShipOverlapException ignored) {
            }
        }
    }

    @Override
    public Coordinates chooseMove() {
        while (true) {
            try {
                // generate random coordinates
                int x = random.nextInt(playerBoard.getWidth());
                int y = random.nextInt(playerBoard.getHeight());
                Coordinates coordinates = new Coordinates(x, y);
                coordinates.checkBounds(playerBoard.getWidth(), playerBoard.getHeight());

                if(!enemyBoard.hasGuessedAlready(coordinates)) {
                    return coordinates;
                }
            } catch(IndexOutOfBoundsException e) {
            }
        }
    }

    @Override
    public View getView() {
        return new ComputerView ();
    }
}
