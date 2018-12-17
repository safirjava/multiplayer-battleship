package com.safir.battleship;

import com.safir.battleship.game.Game;
import com.safir.battleship.player.NetworkPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;

/**
 * @author safir
 */
public class BattleShipServer {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket (NetworkPlayer.PORT);
        while(true) {
            Game game = new Game (
                    new NetworkPlayer(listener, WIDTH, HEIGHT, BattleShips.SHIPS.length),
                    new NetworkPlayer(listener, WIDTH, HEIGHT, BattleShips.SHIPS.length),
                    Arrays.asList(BattleShips.SHIPS)
            );
            new Thread(game).start();
        }
    }
}
