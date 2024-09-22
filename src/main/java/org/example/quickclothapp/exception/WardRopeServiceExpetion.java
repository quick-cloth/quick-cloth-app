package org.example.quickclothapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardRopeServiceExpetion extends Exception{
    int statusCode;
    public WardRopeServiceExpetion(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
