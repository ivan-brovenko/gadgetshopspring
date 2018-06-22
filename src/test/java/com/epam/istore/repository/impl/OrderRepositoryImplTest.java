package com.epam.istore.repository.impl;

import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryImplTest {
    private final static String ADD_ORDER = "INSERT INTO gadget_shop.order VALUES (DEFAULT ,DEFAULT ,DEFAULT ,?,?,?,?,?,?)";
    private final static String GET_SHIP_ID_BY_NAME = "SELECT ship.id FROM ship WHERE name = ?";
    private final static String GET_BILL_ID_BY_NAME = "SELECT  bill.id FROM bill WHERE bill.name = ?";
    @Mock
    private Connection connection;
    @Mock
    private ConnectionHolder connectionHolder;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Order order;
    @InjectMocks
    private OrderRepositoryImpl orderRepository;
    @Mock
    private PreparedStatement billingStatement;
    @Mock
    private PreparedStatement shippingStatement;
    @Mock
    private ResultSet billingResultSet;
    @Mock
    private ResultSet shippingResultSet;

    @Before
    public void setUp() throws SQLException {
        when(connectionHolder.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(connection.prepareStatement(GET_BILL_ID_BY_NAME)).thenReturn(billingStatement);
        when(connection.prepareStatement(GET_SHIP_ID_BY_NAME)).thenReturn(shippingStatement);
        when(billingStatement.executeQuery()).thenReturn(billingResultSet);
        when(shippingStatement.executeQuery()).thenReturn(shippingResultSet);
        when(billingResultSet.next()).thenReturn(true);
        when(shippingResultSet.next()).thenReturn(true);
    }

    @Test
    public void testAddOrderOnProperMethodInvocation() throws RepositoryException, SQLException {
        orderRepository.addOrder(order);
        verify(connectionHolder,times(3)).getConnection();
        verify(preparedStatement).getGeneratedKeys();
    }
}