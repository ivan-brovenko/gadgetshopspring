package com.epam.istore.repository;


import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;

public interface OrderRepository {
    boolean addOrder(Order order) throws RepositoryException;
}
