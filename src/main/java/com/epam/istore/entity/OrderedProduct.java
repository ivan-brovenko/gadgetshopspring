package com.epam.istore.entity;


import java.util.HashMap;
import java.util.Map;

public final class OrderedProduct {
    private final int productId;
    private final int productCount;
    private final double oneProductPrice;

    public OrderedProduct(int productId, int productCount, double productPrice) {
        this.productId = productId;
        this.productCount = productCount;
        this.oneProductPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public double getOneProductPrice() {
        return oneProductPrice;
    }
}
