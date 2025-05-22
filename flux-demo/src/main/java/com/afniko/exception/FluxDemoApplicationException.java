package com.afniko.exception;

public abstract class FluxDemoApplicationException extends RuntimeException {

    public FluxDemoApplicationException(String message) {
        super(message);
    }

    public FluxDemoApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FluxDemoApplicationException(Throwable cause) {
        super(cause);
    }
}
