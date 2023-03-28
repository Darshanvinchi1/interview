package com.darshan.interview.exception;

public class TokenRefreshException extends RuntimeException{
    private static final Integer serialVersionUID = 1;

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
