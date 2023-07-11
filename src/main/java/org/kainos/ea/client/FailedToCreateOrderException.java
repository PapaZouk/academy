package org.kainos.ea.client;

public class FailedToCreateOrderException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to create new order in the database";
    }
}
