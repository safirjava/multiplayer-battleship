package com.safir.battleship.ship;

import com.safir.battleship.grid.Coordinates;
import com.safir.battleship.grid.Orientation;

/**
 * @author safir
 */
public class ShipImpl implements Ship {
    private final int length;
    private int health;
    private final Orientation orientation;
    private final Coordinates startingPosition;

    public ShipImpl(int length, Coordinates startingPosition, Orientation orientation) {
        if(length < 1) {
            throw new IllegalArgumentException("Incorrect length of: " + length);
        } else if(orientation == null) {
            throw new IllegalArgumentException("Null orientation");
        } else if(startingPosition == null) {
            throw new IllegalArgumentException("Null startingPosition");
        } else if(startingPosition.x < 0 || startingPosition.y < 0) {
            throw new IllegalArgumentException("Incorrect starting position "
                    + "for x: " + startingPosition.x
                    + " or y: " + startingPosition.y
            );
        } else {
            this.length = length;
            this.health = length;
            this.orientation = orientation;
            this.startingPosition = startingPosition;
        }
    }
    @Override
    public int getLength() {
        return length;
    }

    @Override
    public Coordinates getEndPosition() {
        switch(orientation){
            case VERTICAL:
                return new Coordinates(startingPosition.x,startingPosition.y+length-1);
            case HORIZONTAL:
                return new Coordinates(startingPosition.x+length-1,startingPosition.y);
        }
        throw new IllegalStateException("Null orientation");
    }

    @Override
    public boolean isAt(Coordinates coordinates) {
        Coordinates endPosition = getEndPosition();
        switch(orientation){
            case VERTICAL:
                return coordinates.x == startingPosition.x
                        && coordinates.y >= startingPosition.y
                        && coordinates.y <= endPosition.y;
            case HORIZONTAL:
                return coordinates.y == startingPosition.y
                        && coordinates.x >= startingPosition.x
                        && coordinates.x <= endPosition.x;
        }
        throw new IllegalStateException("Null orientation");
    }

    @Override
    public void takeAHit() {
        if(isSunk()){
            throw new IllegalStateException("Ship has already sunk.");
        }

        health--;
    }

    @Override
    public boolean isSunk() {
        return health == 0;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Coordinates[] getAllCoordinates() {
        Coordinates[] allCoordinates = new Coordinates[length];
        switch(orientation){
            case VERTICAL:
                for(int i = 0; i < length; i++) {
                    allCoordinates[i] = new Coordinates(startingPosition.x,startingPosition.y+i);
                }
                break;
            case HORIZONTAL:
                for(int i = 0; i < length; i++) {
                    allCoordinates[i] = new Coordinates(startingPosition.x+i,startingPosition.y);
                }
                break;
        }
        return allCoordinates;
    }

    @Override
    public Coordinates getStartingPosition() {
        return startingPosition;
    }
}
