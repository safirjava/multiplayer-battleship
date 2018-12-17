package com.safir.battleship;

import com.safir.battleship.game.Game;
import com.safir.battleship.player.ComputerPlayerStrategy;
import com.safir.battleship.player.HumanPlayer;
import com.safir.battleship.player.Player;
import com.safir.battleship.ship.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author safir
 */
public class BattleShips {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final ShipType[] SHIPS = { new AircraftCarrierShip (),
            new BattleshipShip (),
            new CruiserShip (),
            new SubmarineShip (),
            new DestroyerShip ()};

    private final BufferedReader keyboard;

    private BattleShips(){
        InputStreamReader isr = new InputStreamReader(System.in);
        keyboard = new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        BattleShips battleShips=new BattleShips ();

        System.out.println("*** WELCOME TO BATTLESHIPS *** ");
        System.out.println("1: to Play against Computer");
        System.out.println("2: to Play against another Player");
        System.out.print(">>> ");
        int gameType = Integer.parseInt (battleShips.keyboard.readLine ());
        if(gameType == 1){
            Player humanPlayer=new HumanPlayer (WIDTH, HEIGHT, SHIPS.length);
            Player computerPlayer=new ComputerPlayerStrategy (WIDTH, HEIGHT, SHIPS.length);
            Game game = new Game(humanPlayer, computerPlayer, Arrays.asList (SHIPS));
            game.start ();
        }
        else if (gameType == 2){
            System.out.println ("Enter server host name");
            System.out.print(">>> ");
            String hostname = battleShips.keyboard.readLine ();
            new BattleShipClient (hostname).start ();
        }
        else {
            System.out.println("This is not a valid input!");
        }
    }

}
