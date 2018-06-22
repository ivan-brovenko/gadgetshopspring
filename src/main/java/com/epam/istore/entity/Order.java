package com.epam.istore.entity;


import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private String status;
    private LocalDateTime dateOfMakingAnOrder;
    private int userId;
    private String creditCard;
    private int cvv;
    private String details;
    private String shipping;
    private String billing;
    private String address;
    private double totalPrice;
    private List<OrderedProduct> listOfOrderedProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDate() {
        return dateOfMakingAnOrder;
    }

    public void setDate(LocalDateTime date) {
        this.dateOfMakingAnOrder = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderedProduct> getListOfOrderedProducts() {
        return listOfOrderedProducts;
    }

    public void setListOfOrderedProducts(List<OrderedProduct> listOfOrderedProducts) {
        this.listOfOrderedProducts = listOfOrderedProducts;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
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
