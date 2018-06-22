package com.epam.istore.bean;


import com.epam.istore.entity.OrderedProduct;

import java.util.List;

public class OrderBean {
    private String cartNumber;
    private String cvvNumber;
    private String shipping;
    private String billing;
    private int userId;
    private String address;
    private double totalPrice;
    private List<OrderedProduct> orderedProducts;

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}