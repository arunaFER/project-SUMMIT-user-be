package com.B2B.SP.user.exceptions.customexceptions;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserValidationException(String message){
        super(message);
    }

    public UserValidationException(Throwable cause){
        super(cause);
    }
}
