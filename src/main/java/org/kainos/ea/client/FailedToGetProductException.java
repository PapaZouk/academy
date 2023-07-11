package org.kainos.ea.client;

public class FailedToGetProductException extends Throwable{

    @Override
    public String getMessage() {
        return "Failed to retrieved product from the database";
    }
}
