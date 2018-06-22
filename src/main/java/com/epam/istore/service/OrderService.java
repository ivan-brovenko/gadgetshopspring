package com.epam.istore.service;


import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;

public interface OrderService {
    void addOrder(Order order) throws ServiceException;
}
