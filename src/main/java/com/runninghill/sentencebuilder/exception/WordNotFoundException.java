package com.runninghill.sentencebuilder.exception;

public class WordNotFoundException extends RuntimeException {

    private String message;
    public WordNotFoundException(String message) {
        this.message = message;
    }
}
