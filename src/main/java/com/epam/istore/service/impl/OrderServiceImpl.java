package com.epam.istore.service.impl;


import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.repository.OrderRepository;
import com.epam.istore.service.OrderService;
import org.apache.log4j.Logger;
import com.epam.istore.transaction.TransactionManager;

public class OrderServiceImpl implements OrderService {
    private final static Logger LOGGER = Logger.getRootLogger();
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(Order order) throws ServiceException {
        try {
            orderRepository.addOrder(order);
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

    }
}
