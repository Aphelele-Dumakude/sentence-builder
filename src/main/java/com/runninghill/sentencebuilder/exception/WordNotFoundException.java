package com.runninghill.sentencebuilder.exception;

public class WordNotFoundException extends RuntimeException {

    private final String message;
    public WordNotFoundException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
