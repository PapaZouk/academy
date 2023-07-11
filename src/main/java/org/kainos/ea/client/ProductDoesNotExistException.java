package org.kainos.ea.client;

public class ProductDoesNotExistException extends Throwable {

    @Override
    public String getMessage() {
        return "Could not find a product in the database";
    }
}
