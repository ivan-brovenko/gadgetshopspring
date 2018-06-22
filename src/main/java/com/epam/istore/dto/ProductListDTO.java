package com.epam.istore.dto;

import com.epam.istore.entity.Product;

import java.util.List;

public class ProductListDTO {
    private List<Product> products;
    private int numberOfPages;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
