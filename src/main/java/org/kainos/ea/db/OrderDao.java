package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao  {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<Order> getAllOrders() throws SQLException {
        Connection c = databaseConnector.getConnection();

            String query = "SELECT OrderID, CustomerID, OrderDate FROM `Order`";

            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(query);

            List<Order> orders = new ArrayList<>();

            while (result.next()) {
                Order order = Order.builder()
                        .orderId(result.getInt("OrderID"))
                        .customerId(result.getInt("CustomerID"))
                        .orderDate(result.getTimestamp("OrderDate"))
                        .build();
                orders.add(order);
            }

            return orders;
    }

    public Order getOrderById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String query = "SELECT OrderID, CustomerID, OrderDate FROM `Order`";

        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            return Order.builder()
                    .orderId(result.getInt("OrderID"))
                    .customerId(result.getInt("CustomerID"))
                    .orderDate(result.getTimestamp("OrderDate"))
                    .build();
        }
        return null;
    }

    public int createNewOrder(OrderRequest order) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String query = "INSERT INTO `Order` (CustomerID, OrderDate) " +
                "VALUES (?, ?)";

        PreparedStatement statement = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, order.getCustomerId());
        statement.setDate(2, new Date(order.getOrderDate().getTime()));

        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public void updateOrder(int id, OrderRequest order)  throws SQLException {
        Connection c = databaseConnector.getConnection();

        String query = "UPDATE `Order` SET CustomerID = ?, OrderDate = ? WHERE OrderID = ?";

        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, order.getCustomerId());
        statement.setDate(2, new Date(order.getOrderDate().getTime()));
        statement.setInt(3, id);

        statement.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String query = "DELETE FROM `Order` WHERE OrderID = ?";

        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
    }
}
