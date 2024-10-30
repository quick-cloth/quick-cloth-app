package org.example.quickclothapp.exception;

import lombok.Getter;

@Getter
public class DataServiceException extends Exception{
    int statusCode;
    public DataServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
