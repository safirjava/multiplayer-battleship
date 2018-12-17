package com.safir.battleship.player;

import com.safir.battleship.Exception.MalformattedException;
import com.safir.battleship.Exception.QuitException;
import com.safir.battleship.Exception.ResignException;
import com.safir.battleship.Exception.ShipOverlapException;
import com.safir.battleship.game.TextView;
import com.safir.battleship.game.View;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.Orientation;
import com.safir.battleship.ship.Ship;
import com.safir.battleship.ship.ShipImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author safir
 */
public class HumanPlayer extends AbstractPlayer {
    private final BufferedReader keyboard;

    public HumanPlayer(int newBoardWidth, int newBoardHeight, int newShipsRemaining) {
        super(newBoardWidth, newBoardHeight, newShipsRemaining);

        InputStreamReader isr = new InputStreamReader(System.in);
        keyboard = new BufferedReader(isr);
    }

    @Override
    public void placeShipOnToPlayerBoard(int lengthOfShip) throws ResignException {
        while (true) {
            try {
                System.out.println("Please enter the starting coordinates for this ship (length "+lengthOfShip+"):");
                System.out.print(">>> ");
                String coordinatesStr = keyboard.readLine ();
                if(coordinatesStr.equalsIgnoreCase("resign")) {
                    throw new ResignException ();
                }
                Coordinates coordinates = new Coordinates(coordinatesStr);
                coordinates.checkBounds(playerBoard.getWidth(), playerBoard.getHeight());

                // optional improvement: check if there are 0, 1 or 2 valid orientations

                Ship ship = null;
                while(ship == null) {
                    System.out.println("Please enter an orientation for this ship:");
                    System.out.println ("- 1. For horizontal");
                    System.out.println ("- 2. For vertical");
                    System.out.print(">>> ");
                    String orientationStr = keyboard.readLine();
                    if(orientationStr.equalsIgnoreCase("resign")) {
                        throw new ResignException();
                    } else if (orientationStr.equals ("1")) {
                        ship = new ShipImpl (lengthOfShip, coordinates, Orientation.HORIZONTAL);
                    } else if (orientationStr.equals ("2")) {
                        ship = new ShipImpl(lengthOfShip, coordinates, Orientation.VERTICAL);
                    } else {
                        System.out.println("Invalid orientation entered");
                    }
                }

                playerBoard.placeShip(ship);
                break;
            } catch(MalformattedException e) {
                System.out.println("Invalid coordinates entered: " + e.getMessage());
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Ship placed out of bounds");
            } catch(ShipOverlapException e) {
                System.out.println("This ship overlaps with another");
            } catch(IOException e) {
                throw new IllegalStateException("IOException was thrown, with message: " + e.getMessage());
            }
        }
    }

    @Override
    public Coordinates chooseMove() throws QuitException {
        while (true) {
            try {
                System.out.println("Please enter coordinates to fire at:");
                System.out.print(">>> ");
                String coordinatesStr = keyboard.readLine();
                if(coordinatesStr.equalsIgnoreCase("resign")) {
                    throw new ResignException();
                }
                Coordinates coordinates = new Coordinates(coordinatesStr);
                if(enemyBoard.hasGuessedAlready(coordinates)) {
                    System.out.println("Coordinates already guessed.");
                } else {
                    return coordinates;
                }
            } catch(MalformattedException e) {
                System.out.println("Invalid coordinates entered.");
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Coordinates out of bounds.");
            } catch(IOException e) {
                throw new IllegalStateException("IOException was thrown, with message: " + e.getMessage());
            }
        }
    }

    @Override
    public View getView() {
        return new TextView ();
    }
}
