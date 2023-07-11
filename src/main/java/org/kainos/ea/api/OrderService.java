package org.kainos.ea.api;

import org.kainos.ea.cli.*;
import org.kainos.ea.client.*;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private OrderDao orderDao = new OrderDao();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {
        List<Order> allOrders = null;
        try {
            allOrders = orderDao.getAllOrders();
//            exercises(allOrders);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetOrdersException();
        }

        return allOrders;
    }

    public Order getOrderById(int id) throws FailedToGetOrdersException, OrderDoesNotExistException {
        try {
            Order order = orderDao.getOrderById(id);

            if (order == null) {
                throw new OrderDoesNotExistException();
            }

            return orderDao.getOrderById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetOrdersException();
        }
    }


    public int createNewOrder(OrderRequest order) throws FailedToCreateOrderException {
        try {
            int id = orderDao.createNewOrder(order);

            if (id == -1) {
                throw new FailedToCreateOrderException();
            }

            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateOrderException();
        }
    }

    public void updateOrder(int id, OrderRequest order)
            throws FailedToUpdateOrderException,
            OrderDoesNotExistException
    {
        try {
            Order orderToUpdate = orderDao.getOrderById(id);

            if (orderToUpdate == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.updateOrder(id, order);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateOrderException();
        }
    }

    public void deleteProduct(int id) throws OrderDoesNotExistException, FailedToDeleteOrderException {
        try {
            Order orderToDelete = orderDao.getOrderById(id);

            if (orderToDelete == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.deleteProduct(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToDeleteOrderException();
        }
    }

    private void exercises(List<Order> allOrders) {
        try {
//            allOrders.get(1000);
            printOrderDetails(allOrders);
            printOrderByDateDesc(allOrders);
            printOrdersFromTheLastWeek(allOrders);
            printOrderWithProvidedCustomerID(allOrders, 1);
            printOrderWithTheMostRecent(allOrders);
            printMostRecentOrders(allOrders);
            printCountedNumberOfOrders(allOrders);
            printCustomerWithTheMostOrders(allOrders);
            printCustomerWithTheLeastOrders(allOrders);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    // 21
    private void printOrderDetails(List<Order> allOrders) {
        for (Order order : allOrders) {
            System.out.printf("#### EX.21: ORDER ID: [%s], CUSTOMER ID: [%s], ORDER DATE: [%s]%n",
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getOrderDate());
        }
    }

    // 22
    private void printOrderByDateDesc(List<Order> allOrders) {
        System.out.println("#### EX.22: ORDERS LIST BY DATE DESC: ");
        allOrders.sort(Comparator.comparing(Order::getOrderDate));
        allOrders.forEach(System.out::println);
    }

    // 23
    private void printOrdersFromTheLastWeek(List<Order> allOrders) {
        System.out.println("#### EX.23: ORDERS FROM THE LAST WEEK: ");
        try {
            allOrders.stream()
                    .filter(order -> order.getOrderDate()
                            .after(Date.from(Instant.now().minus(7, ChronoUnit.DAYS))))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // 24
    private void printOrderWithProvidedCustomerID(List<Order> allOrders, int id) {
        System.out.println("#### EX.24: CUSTOMER WITH PROVIDED ID: " + id);
        allOrders.stream()
                .filter(order -> order.getCustomerId() == id)
                .forEach(System.out::println);
    }

    // 25
    private void printOrderWithTheMostRecent(List<Order> allOrders) {
        Order mostRecent = Collections.max(allOrders);
        System.out.println("#### EX.25: MOST RECENT ORDER: " + mostRecent);
    }

    // 26
    private void printMostRecentOrders(List<Order> allOrders) {
        Order oldestOrder = Collections.min(allOrders);
        System.out.println("#### EX.26: OLDEST ORDER: " + oldestOrder);
    }

    // 27
    private void printCountedNumberOfOrders(List<Order> allOrders) {
        long count = allOrders.size();
        System.out.println("#### EX.27: COUNTED ALL ORDERS: " + count);
    }

    // 28
    private void printCustomerWithTheMostOrders(List<Order> allOrders) {
        Map.Entry<Integer, Long> client = allOrders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        TreeMap::new,
                        Collectors.counting()
                )).entrySet().stream()
                .max(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .get();

        System.out.printf("#### EX.28: CLIENT WITH THE MOST ORDERS: [%s], WITH NUMBER OF ORDERS: [%s]%n",
                client.getKey(),
                client.getValue());

    }

    // 29
//    private void printCustomerWithTheLeastOrders(List<Order> allOrders) {
//        System.out.println("#### CUSTOMER WITH THE LEAST ORDERS: ");
//
//        TreeMap<Integer, Long> clientsOrders = allOrders.stream()
//                .collect(Collectors.groupingBy(
//                        Order::getCustomerId,
//                        TreeMap::new,
//                        Collectors.counting()
//                ));
//
//        Integer min = Collections.min(clientsOrders.keySet());
//        System.out.printf("#### CLIENT WITH THE LEAST ORDERS: [%s], HAD ORDERS: [%s]%n",
//                min,
//                clientsOrders.get(min));
//    }

    // 29
    private void printCustomerWithTheLeastOrders(List<Order> allOrders) {
        Map.Entry<Integer, Long> client = allOrders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        TreeMap::new,
                        Collectors.counting()
                )).entrySet().stream()
                .min(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .get();

        System.out.printf("#### EX.29: CLIENT WITH THE LEAST ORDERS: [%s], WITH NUMBER OF ORDERS: [%s]%n",
                client.getKey(),
                client.getValue());

    }

}
