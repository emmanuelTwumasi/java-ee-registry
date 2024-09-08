package com.emma.temp.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof CustomException) {
            return Response.status(((CustomException) exception).getStatus())
                    .entity(exception.getMessage())
                    .build();
        }

        logger.log(java.util.logging.Level.SEVERE, "An unexpected error occurred.", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Something went wrong, kindly try again.")
                .build();

    }
}
