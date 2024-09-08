package com.emma.temp.exception;

import jakarta.ws.rs.core.Response;

public class CustomException extends RuntimeException {
    Response.Status status;
    String message;

    public CustomException(String message, Response.Status status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
