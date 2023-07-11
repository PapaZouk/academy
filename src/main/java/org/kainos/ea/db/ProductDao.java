package org.kainos.ea.db;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProductDao {

    private DatabaseConnector connector = new DatabaseConnector();

    public List<Product> getAllProducts() throws SQLException {
        Connection c = connector.getConnection();

        String query = "SELECT ProductID, Name, Description, Price, CreatedDate, Category FROM Product";

        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(query);

        List<Product> products = new ArrayList<>();

        while (result.next()) {
            Product product = Product.builder()
                    .productId(result.getInt("ProductID"))
                    .name(result.getString("Name"))
                    .description(result.getString("Description"))
                    .price(result.getBigDecimal("Price"))
                    .createdDate(result.getTimestamp("CreatedDate"))
                    .category(result.getString("Category"))
                    .build();
            products.add(product);
        }

        exercises(products);

        return products;
    }

    public Product getProductById(int id) throws SQLException {
        Connection c = connector.getConnection();

        String query = "SELECT ProductID, Name, Description, Price, CreatedDate, Category" +
                " FROM Product WHERE ProductID = " + id;

        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            return Product.builder()
                    .productId(result.getInt("ProductID"))
                    .name(result.getString("Name"))
                    .description(result.getString("Description"))
                    .createdDate(result.getDate("CreatedDate"))
                    .category(result.getString("Category"))
                    .build();
        }
        return null;
    }

    public int createProduct(ProductRequest product) throws SQLException {
        Connection c = connector.getConnection();

        String query = "INSERT INTO Product (Name, Description, Price, CreatedDate, Category)" +
                " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setBigDecimal(3, product.getPrice());
        statement.setDate(4, new Date(product.getCreatedDate().getTime()));
        statement.setString(5, product.getCategory());

        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public void updateProduct(int id, ProductRequest product) throws SQLException {
        Connection c = connector.getConnection();

        String query = "UPDATE Product SET Name = ?, Description = ?, Price = ? WHERE ProductID = ?";

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, product.getName());
        statement.setString(2, product.getName());
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, id);

        statement.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        Connection c = connector.getConnection();

        String query = "DELETE FROM Product WHERE ProductID = ?";

        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private void exercises(List<Product> products) {
        printTotalPriceOfAllProductsWithForLoop(products);
        printTotalPriceOfAllProductsWithForEachLoop(products);
        printTotalPriceOfAllProductsWithWhileLoop(products);
        printTotalPriceOfAllProducts(products);
        printTotalPriceOfProducts(products);
        printTotalPriceOfProductsWithPriceOver100(products);
        printTotalPriceOfAllProductsWithCostLessThan100AndTotalProductsOfCostMoreThan100(products);
        printProductsWithSpecificName(products);
        printListOfValues();
        printListOfUniqueValues();
        printSortedValues();
    }

    // 1
    private void printTotalPriceOfAllProductsWithForLoop(List<Product> products) {
        double sum = 0;
        for (int i = 0; i < products.size(); i++) {
            sum += (products.get(i).getPrice().doubleValue());
        }
        System.out.printf("#### EX.1: TOTAL PRICE OF ALL PRODUCTS: $%s",
                BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN));

    }

    // 2
    private void printTotalPriceOfAllProductsWithForEachLoop(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice().doubleValue();
        }
        System.out.printf("#### EX.2: TOTAL PRICE OF ALL PRODUCTS: %s",
                BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN));
    }

    // 3
    private void printTotalPriceOfAllProductsWithWhileLoop(List<Product> products) {
        double sum = 0;
        Iterator<Product> iterator = products.listIterator();

        while (iterator.hasNext()) {
            Product nextProduct = iterator.next();
            sum += nextProduct.getPrice().doubleValue();
        }
        System.out.printf("#### EX.3: TOTAL PRICE OF ALL PRODUCTS: $%s",
                BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN));
    }

    // 4
    private void printTotalPriceOfAllProducts(List<Product> products) {
        Iterator<Product> productIterator = products.listIterator();

        double sum = 0;

        do {
            Product product = productIterator.next();
            sum += product.getPrice().doubleValue();
        } while (productIterator.hasNext());

        System.out.printf("#### EX.4: TOTAL PRICE OF ALL PRODUCTS: $%s",
                BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN));

    }

    // 5
    private void printTotalPriceOfProducts(List<Product> products) {
        BigDecimal sum = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        System.out.println("\n#### EX.5: TOTAL PRICE OF ALL PRODUCTS: " + sum);
    }


    // 6
    private void printTotalPriceOfProductsWithPriceOver100(List<Product> products) {
        BigDecimal sum = products.stream()
                .map(Product::getPrice)
                .filter(prod -> prod.compareTo(new BigDecimal(100)) < 0)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        System.out.println("\n#### EX.6: TOTAL PRICE OF PRODUCTS WHERE THE PRODUCT PRICE IS LESS THAN 100: " + sum);
    }

    // 7
    private void printTotalPriceOfAllProductsWithCostLessThan100AndTotalProductsOfCostMoreThan100(
            List<Product> products
    ) {
        double totalPriceOfCheapProduct = 0;
        double totalPriceOfExpensiveProducts = 0;

        for (Product product : products) {
            if (product.getPrice().compareTo(new BigDecimal(100)) < 0) {
                totalPriceOfCheapProduct += product.getPrice().doubleValue();
            } else {
                totalPriceOfExpensiveProducts += product.getPrice().doubleValue();
            }
        }
        System.out.printf("%n#### EX.7: TOTAL PRICE OF CHEAP PRODUCTS: $%s%n#### TOTAL PRICE OF EXPENSIVE PRODUCTS: $%s%n",
                totalPriceOfCheapProduct, totalPriceOfExpensiveProducts);
    }

    // 8
    private void printProductsWithSpecificName(List<Product> products) {
        System.out.println("\n#### EX.8: PRODUCTS BY NAME WITH MESSAGE: ");
        ;
        for (Product product : products) {
            switch (product.getName()) {
                case ("X-hyper"):
                    System.out.printf("This is a [%s] product with price: $%s%n",
                            product.getName(),
                            product.getPrice());
                    break;
                case ("Macaroo"):
                    System.out.printf("[%s] products costs [%s] and are part of [%s] category.%n",
                            product.getName(),
                            product.getPrice(),
                            product.getCategory());
                case ("Toto"):
                    System.out.printf("[%s] is a [%s] and costs [%s].%n",
                            product.getName(),
                            product.getDescription(),
                            product.getPrice());
                default:
                    System.out.printf("[%s] is from new collection",
                            product.getName());
            }
        }
    }

    // 9
    private void printListOfValues() {
        System.out.println("#### EX.9: LIST WITH DUPLICATED VALUES: ");
        List<Integer> integers = Arrays.asList(1, 4, 4, 5, 7, 9, 9);
        integers.forEach(System.out::println);
    }

    // 10
    private void printListOfUniqueValues() {
        System.out.println("#### EX.10 COLLECTION OF UNIQUE VALUES: ");
        List<Integer> integers = Arrays.asList(1, 1, 4, 5, 6, 6, 3);
        Set<Integer> integerSet = new HashSet<>(integers);
        integerSet.forEach(System.out::println);
    }

    // 11
    private void printSortedValues() {
        System.out.println("#### EX.11 SORTED VALUES: ");
        List<Integer> integers = Arrays.asList(1, 1, 4, 5, 6, 6, 3);
        Collections.sort(integers, Comparator.naturalOrder());
        integers.forEach(System.out::println);
    }


}
