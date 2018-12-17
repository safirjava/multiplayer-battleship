package com.safir.battleship.player;

import com.safir.battleship.Exception.MalformattedException;
import com.safir.battleship.Exception.QuitException;
import com.safir.battleship.board.EnemyBoard;
import com.safir.battleship.board.PlayerBoard;
import com.safir.battleship.game.GameOverMessage;
import com.safir.battleship.game.View;
import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.CoordinatesState;
import com.safir.battleship.ship.ShipType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author safir
 */
public class NetworkPlayer implements Player, View {
    public static final int PORT = 9090;
    Socket socket = null;

    private final BufferedReader in;
    private final PrintWriter out;
    private int shipsRemaining;

    public NetworkPlayer(ServerSocket listener, int width, int height, int newShipsRemaining) throws IOException {
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("You cannot make a grid size of "
                    + width + " by " + height );
        } else if(newShipsRemaining <= 0) {
            throw new IllegalArgumentException("You cannot make a grid with no ships remaining.");
        }

        shipsRemaining = newShipsRemaining;

        socket = listener.accept();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(isr);
        out = new PrintWriter(socket.getOutputStream());

        sendf("START %d %d %d", width, height, newShipsRemaining);
    }

    private void send(String data) {
        out.println(data);
        out.flush();
    }
    private void sendf(String data, Object... args) {
        out.printf(data + "\r\n", args);
        out.flush();
    }

    private String receive() throws QuitException {
        try {
            String command = in.readLine();
            if(command == null) {
                throw new QuitException();
            }
            return command;
        } catch(IOException e) {
            System.err.println("The client has unexpectedly disconnected");
            throw new QuitException();
        }
    }


    @Override
    public void placeShipOnToPlayerBoard(int lengthOfShip) {
        send("PLACE_SHIP " + lengthOfShip);
    }

    @Override
    public Coordinates chooseMove() throws QuitException {
        send("CHOOSE_A_MOVE");
        String response = receive();
        if(response.equals("RESIGN")) {
            throw new QuitException();
        } else{
            try {
                return new Coordinates(response);
            } catch(MalformattedException e) {
                System.err.println("Received " + response + ", expected: Coordinates (e.g. A1)");
                throw new QuitException();
            }
        }
    }

    @Override
    public CoordinatesState takeHit(Coordinates coordinates) throws QuitException {
        send("TAKE_HIT_AT " + coordinates);

        // potential improvement: to use the enum.valueOf method to convert a string to a cell state
        String response = receive();
        if(response.equals("MISS")) {
            return CoordinatesState.MISS;
        } else if(response.equals("SHIP_HIT")) {
            return CoordinatesState.SHIP_HIT;
        } else if(response.equals("SHIP_SUNK")) {
            if(shipsRemaining == 0) {
                System.err.println("Synchronisation error (shipsRemaining)");
                throw new QuitException();
            }

            shipsRemaining--;
            return CoordinatesState.SHIP_SUNK;
        } else {
            System.err.println("Received " + response + ", expected CellState (e.g. MISS)");
            throw new QuitException();
        }
    }

    @Override
    public boolean hasLost() {
        return shipsRemaining == 0;
    }

    @Override
    public void announceGameOver(GameOverMessage message) {
        send("GAME_OVER " + message);
        if (socket != null) {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    @Override
    public boolean hasAlreadyGuessed(Coordinates coordinates) {
        throw new NotImplementedException ();
    }

    @Override
    public void updateEnemyBoard(Coordinates coordinates, CoordinatesState cellState) {
        sendf("MOVE_RESPONSE %s %s", coordinates, cellState);
    }

    @Override
    public View getView() {
        return this;
    }

    // These should do nothing

    @Override
    public void viewState() {}

    @Override
    public void viewBoards(PlayerBoard playerBoard, EnemyBoard enemyBoard) {}

    @Override
    public void welcomeUser() {}

    @Override
    public void viewInstructions() {}

    @Override
    public void viewResultOfMove(Coordinates coordinates, CoordinatesState cellState) {}

    @Override
    public void viewResultOfEnemyMove(Coordinates coordinates, CoordinatesState cellState) {}

    @Override
    public void viewShipsLeftToPlace(List<ShipType> shipTypes) {}
}
