package com.epam.istore.converter.impl;

import com.epam.istore.bean.OrderBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {
    @Mock
    private OrderBean orderBean;
    private OrderConverter orderConverter = new OrderConverter();

    @Test
    public void testOrderConverterOnConvertingOrderBeanToOrder() {
        orderConverter.convert(orderBean);
        inOrder(orderBean).verify(orderBean).getUserId();

    }
}