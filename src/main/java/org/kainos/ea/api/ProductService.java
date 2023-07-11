package org.kainos.ea.api;

import org.kainos.ea.cli.*;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductDao productDao = new ProductDao();
    private final ProductValidator validator = new ProductValidator();

    public List<Product> getAllProducts() throws FailedToGetProductsException {
        try {
            List<Product> allProducts = productDao.getAllProducts();
            exercises(allProducts);
            return allProducts;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetProductsException();
        }
    }

    public Product getProductById(int id)
            throws FailedToGetProductException, ProductDoesNotExistException {
        try {
            Product product = productDao.getProductById(id);

            if (product == null) {
                throw new ProductDoesNotExistException();
            }
            return product;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetProductException();
        }
    }

    public int createNewProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        try {
            String validation = validator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            int id = productDao.createProduct(product);

            if (id == -1) {
                throw new FailedToCreateProductException();
            }

            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToCreateProductException();
        }
    }

    public void updateProduct(int id, ProductRequest product)
            throws InvalidProductException,
            ProductDoesNotExistException,
            FailedToUpdateProductException
    {
        try {
            String validation = validator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            Product productToUpdate = productDao.getProductById(id);

            if (productToUpdate == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.updateProduct(id, product);

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateProductException();
        }
    }

    public void deleteProduct(int id)
            throws ProductDoesNotExistException,
            FailedToDeleteProductException
    {
        try {
            Product productToDelete = productDao.getProductById(id);

            if (productToDelete == null) {
                throw new ProductDoesNotExistException();
            }
            productDao.deleteProduct(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToDeleteProductException();
        }
    }

    private void exercises(List<Product> allProducts) {
        Collections.sort(allProducts);
        System.out.println("#### SORTED PRODUCTS BY PRICE:");
        allProducts.forEach(System.out::println);
        System.out.println();

        System.out.printf("#### EX.17: CHEAPEST PRODUCT: %s%n", Collections.min(allProducts));
        System.out.printf("#### EX.18: THE MOST EXPENSIVE PRODUCT: %s%n", Collections.max(allProducts));

        System.out.println("#### Ex.19: PRODUCTS WITH PRICE HIGHER THAN 10: ");
        allProducts.stream()
                .filter(product -> product.getPrice().doubleValue() > 10)
                .forEach(System.out::println);

        List<Product> cheapProducts = allProducts.stream()
                .filter(product -> product.getPrice().doubleValue() < 10)
                .collect(Collectors.toList());
        System.out.print("\n#### EX.20: NEW LIST OF CHEAPEST PRODUCTS: \n");
        cheapProducts.forEach(System.out::println);

    }
}
