package com.safir.battleship.ship;

public class BattleshipShip implements ShipType {
    private final int length = 4;
    private final String name = "Battleship";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLength() {
        return length;
    }
}
