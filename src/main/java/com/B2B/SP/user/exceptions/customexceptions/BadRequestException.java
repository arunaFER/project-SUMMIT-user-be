package com.B2B.SP.user.exceptions.customexceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(Throwable cause){
        super(cause);
    }
}
