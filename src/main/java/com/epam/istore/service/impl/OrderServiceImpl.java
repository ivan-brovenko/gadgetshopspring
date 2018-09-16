package com.epam.istore.service.impl;


import com.epam.istore.model.Order;
import com.epam.istore.repository.OrderRepository;
import com.epam.istore.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }
}
