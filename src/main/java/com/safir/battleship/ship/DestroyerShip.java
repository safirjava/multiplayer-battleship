package com.safir.battleship.ship;

public class DestroyerShip implements ShipType {
    private final int length = 2;
    private final String name = "Destroyer";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLength() {
        return length;
    }
}
