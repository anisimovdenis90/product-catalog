package com.anisimov.denis.exceptions;

import java.util.Date;

public class CatalogError {
    private String message;
    private Date timestamp;

    public CatalogError(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
