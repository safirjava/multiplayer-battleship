package com.safir.battleship.game;

import com.safir.battleship.Exception.QuitException;
import com.safir.battleship.Exception.ResignException;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.player.Player;
import com.safir.battleship.ship.ShipType;

import java.util.List;

/**
 * @author safir
 */
public class Game extends Thread {
    Player player1, player2, nextToPlay;
    List<ShipType> shipTypes;

    public Game(Player newPlayer1, Player newPlayer2, List<ShipType> shipLengths){
        player1 = newPlayer1;
        player2 =  newPlayer2;
        nextToPlay = player1;
        this.shipTypes = shipLengths;
    }

    public Player getEnemy() {
        if(nextToPlay == player1){
            return player2;
        } else {
            return player1;
        }
    }

    private void placeShips(Player player) throws ResignException {
        player.getView().viewShipsLeftToPlace(shipTypes);
        for(ShipType shipType : shipTypes) {
            player.viewState();
            player.placeShipOnToPlayerBoard(shipType.getLength ());
        }
    }

    public void play() {
        try {
            player1.getView().welcomeUser();
            player1.getView().viewInstructions();

            player2.getView().welcomeUser();
            player2.getView().viewInstructions();

            placeShips(player1);
            placeShips(player2);

            while (true) {
                View view = nextToPlay.getView();
                nextToPlay.viewState();
                Coordinates move = nextToPlay.chooseMove();
                CoordinatesState result = getEnemy().takeHit(move);
                nextToPlay.updateEnemyBoard(move, result);
                view.viewResultOfMove(move, result);
                getEnemy().getView().viewResultOfEnemyMove(move, result);


                if(getEnemy().hasLost()) {
                    view.announceGameOver(GameOverMessage.YOU_WON);
                    getEnemy().getView().announceGameOver(GameOverMessage.YOU_LOST);
                    break;
                }

                nextToPlay = getEnemy();
            }
        } catch(ResignException e) {
            nextToPlay.getView().announceGameOver(GameOverMessage.YOU_RESIGNED);
            getEnemy().getView().announceGameOver(GameOverMessage.ENEMY_RESIGNED);
        } catch(QuitException e) {
            player1.getView().announceGameOver(GameOverMessage.UNEXPECTED_QUIT);
            player2.getView().announceGameOver(GameOverMessage.UNEXPECTED_QUIT);
            System.err.println("Player quit unexpectedly");
        }
    }

    @Override
    public void run() {
        play();
    }
}
