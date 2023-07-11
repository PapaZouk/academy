package org.kainos.ea.client;

public class FailedToUpdateProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update the product";
    }
}
