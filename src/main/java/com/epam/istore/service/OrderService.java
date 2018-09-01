package com.epam.istore.service;


import com.epam.istore.model.Order;
import com.epam.istore.exception.ServiceException;

public interface OrderService {
    void addOrder(Order order) throws ServiceException;
}
