package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OrderRequest {
    private int CustomerId;

    private Date OrderDate;

    @JsonCreator
    public OrderRequest(
            @JsonProperty("CustomerID") int customerId,
            @JsonProperty("OrderDate") Date orderDate) {
        CustomerId = customerId;
        OrderDate = orderDate;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }
}
