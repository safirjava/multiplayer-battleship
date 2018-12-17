package com.safir.battleship.ship;

public class SubmarineShip implements ShipType {
    private final int length = 3;
    private final String name = "Submarine";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLength() {
        return length;
    }
}
