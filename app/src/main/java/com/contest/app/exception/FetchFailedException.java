package com.contest.app.exception;

public class FetchFailedException extends Exception{

    public FetchFailedException(String message){
        super(message);
    }

    public FetchFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
