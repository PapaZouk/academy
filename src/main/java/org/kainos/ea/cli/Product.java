package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Product implements Comparable<Product>{
    private int productId;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDate;
    private String category;

    public Product(int productId, String name, String description, BigDecimal price, Date createdDate, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.category = category;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.getPrice().doubleValue(), o.getPrice().doubleValue());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", category='" + category + '\'' +
                '}';
    }

    public static class ProductBuilder {
        private int productId;
        private String name;
        private String description;
        private BigDecimal price;
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy hh:mm:ss")
        private Date createdDate;
        private String category;

        ProductBuilder() {
        }

        public ProductBuilder productId(int productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder createdDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(
                    this.productId,
                    this.name,
                    this.description,
                    this.price,
                    this.createdDate,
                    this.category
            );
        }
    }
}
