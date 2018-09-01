package com.epam.istore.converter.impl;


import com.epam.istore.bean.OrderBean;
import com.epam.istore.converter.Converter;
import com.epam.istore.model.Order;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderConverter implements Converter<OrderBean, Order> {

    @Override
    public Order convert(OrderBean parameterToConvert) {
        Order order = new Order();
        order.setUserId(parameterToConvert.getUserId());
        order.setDate(LocalDateTime.now());
        order.setListOfOrderedProducts(parameterToConvert.getOrderedProducts());
        order.setBilling(parameterToConvert.getBilling());
        order.setShipping(parameterToConvert.getShipping());
        order.setCreditCard(parameterToConvert.getCartNumber());
        if (StringUtils.isNotBlank(parameterToConvert.getCvvNumber()) && StringUtils.isNumeric(parameterToConvert.getCvvNumber())) {
            order.setCvv(Integer.parseInt(parameterToConvert.getCvvNumber()));
        }
        order.setTotalPrice(parameterToConvert.getTotalPrice());
        order.setAddress(parameterToConvert.getAddress());
        return order;
    }
}
