package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Order implements Comparable<Order>{
    private int OrderId;
    private int CustomerId;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
    private Date OrderDate;

    public Order(int orderId, int customerId, Date orderDate) {
        OrderId = orderId;
        CustomerId = customerId;
        OrderDate = orderDate;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
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

    @Override
    public int compareTo(Order o) {
        return this.getOrderDate().compareTo(o.getOrderDate());
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId=" + OrderId +
                ", CustomerId=" + CustomerId +
                ", OrderDate=" + OrderDate +
                '}';
    }

    public static class OrderBuilder {
        private int OrderId;
        private int CustomerId;
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy hh:mm:ss"
        )
        private Date OrderDate;

        OrderBuilder() { }

        public OrderBuilder orderId(int id) {
            this.OrderId = id;
            return this;
        }

        public OrderBuilder customerId(int customerId) {
            this.CustomerId = customerId;
            return this;
        }

        public OrderBuilder orderDate(Date orderDate) {
            this.OrderDate = orderDate;
            return this;
        }

        public Order build() {
            return new Order(
                    this.OrderId,
                    this.CustomerId,
                    this.OrderDate
            );
        }
    }
}
