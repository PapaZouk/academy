package org.kainos.ea.client;

public class OrderDoesNotExistException extends Throwable{
    @Override
    public String getMessage() {
        return "Product does nt exist in the database";
    }
}
