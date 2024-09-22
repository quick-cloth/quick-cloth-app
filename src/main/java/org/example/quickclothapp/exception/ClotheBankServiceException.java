package org.example.quickclothapp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClotheBankServiceException extends Exception{
    int statusCode;
    public ClotheBankServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
