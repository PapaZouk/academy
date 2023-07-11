package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;

    private Date createdDate;
    private String category;

    @JsonCreator
    public ProductRequest(
            @JsonProperty("Name") String name,
            @JsonProperty("Description") String description,
            @JsonProperty("Price") BigDecimal price,
            @JsonProperty("CreatedDate") Date createdDate,
            @JsonProperty("Category") String category
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.category = category;
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
}
