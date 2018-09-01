package com.epam.istore.bean;


import com.epam.istore.model.Product;

public class ProductInCartBean {
    private Product product;
    private int countOfProduct;

    public ProductInCartBean(Product product, int countOfProduct) {
        this.product = product;
        this.countOfProduct = countOfProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCountOfProduct() {
        return countOfProduct;
    }

    public void setCountOfProduct(int countOfProduct) {
        this.countOfProduct = countOfProduct;
    }
}
