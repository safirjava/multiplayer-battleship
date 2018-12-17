package com.safir.battleship.ship;

public class CruiserShip implements ShipType {
    private final int length = 3;
    private final String name = "Cruiser";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLength() {
        return length;
    }
}
