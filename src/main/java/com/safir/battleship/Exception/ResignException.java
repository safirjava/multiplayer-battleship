package com.safir.battleship.Exception;

/**
 * @author safir
 */
public class ResignException extends QuitException {
    public ResignException() {
    }

    public ResignException(String message) {
        super (message);
    }
}
