package com.safir.battleship.Exception;

/**
 * @author safir
 */
public class QuitException extends Exception {
    public QuitException() {
    }

    public QuitException(String message) {
        super (message);
    }
}
