package com.runninghill.sentencebuilder.exception;

import java.time.LocalDate;

public class ErrorDetails {

    private final LocalDate timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(LocalDate timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}

