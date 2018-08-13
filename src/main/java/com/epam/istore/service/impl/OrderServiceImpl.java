package com.epam.istore.service.impl;


import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.repository.OrderRepository;
import com.epam.istore.service.OrderService;
import org.apache.log4j.Logger;
import com.epam.istore.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    private OrderRepository orderRepository;

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
