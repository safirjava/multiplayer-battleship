package com.safir.battleship.ship;

public class AircraftCarrierShip implements ShipType {
    private final int length = 5;
    private final String name = "Aircraft Carrier";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLength() {
        return length;
    }
}
