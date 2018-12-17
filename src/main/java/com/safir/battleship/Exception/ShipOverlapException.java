package com.safir.battleship.Exception;

/**
 * @author safir
 */
public class ShipOverlapException extends Exception {
    public ShipOverlapException() {
    }
    public ShipOverlapException(String message) {
        super (message);
    }
}
