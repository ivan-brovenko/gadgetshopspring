package com.epam.istore.repository;


import com.epam.istore.model.Order;

public interface OrderRepository {
    boolean addOrder(Order order);
}
