package org.kainos.ea.client;

public class FailedToDeleteProductException extends Throwable {

    @Override
    public String getMessage() {
        return "Failed to delete product";
    }
}
