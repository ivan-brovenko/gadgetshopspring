package com.epam.istore.repository.impl;

import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.Order;
import com.epam.istore.entity.OrderedProduct;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;


public class OrderRepositoryImpl implements OrderRepository {
    private final static String ADD_ORDER = "INSERT INTO gadget_shop.order VALUES (DEFAULT ,DEFAULT ,DEFAULT ,?,?,?,?,?,?)";
    private final static String ADD_ITEM = "INSERT INTO gadget_shop.order_gadget VALUES (? ,?,?,?)";
    public static final String ID = "id";
    private final static String GET_SHIP_ID_BY_NAME = "SELECT ship.id FROM ship WHERE name = ?";
    private final static String GET_BILL_ID_BY_NAME = "SELECT  bill.id FROM bill WHERE bill.name = ?";
    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addOrder(Order order) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            int billId = getBillIdByName(order.getBilling());
            int shipId = getShipIdByName(order.getShipping());
            preparedStatement.setString(k++, String.valueOf(order.getDate()));
            preparedStatement.setInt(k++, order.getUserId());
            preparedStatement.setInt(k++, shipId);
            preparedStatement.setInt(k++, billId);
            preparedStatement.setString(k++, order.getAddress());
            preparedStatement.setDouble(k++, order.getTotalPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                order.getListOfOrderedProducts().forEach(orderedProduct -> addItem(orderId,orderedProduct));

//                for (OrderedProduct orderedProduct : order.getListOfOrderedProducts()) {
//                    addItem(orderId, orderedProduct);
//                }
            }
            return preparedStatement;
        });
        return true;
    }


    private int getShipIdByName(String shippingName) {
        return jdbcTemplate.queryForObject(GET_SHIP_ID_BY_NAME,new Object[]{shippingName}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(ID);
            }
        });
    }

    private int getBillIdByName(String billingName) {
        return jdbcTemplate.queryForObject(GET_BILL_ID_BY_NAME,new Object[]{billingName}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return resultSet.getInt(1);
            }
        });
    }

    private boolean addItem(int orderId, OrderedProduct orderedProduct) {
        jdbcTemplate.update(ADD_ITEM, orderId, orderedProduct.getProductId(), orderedProduct.getOneProductPrice(), orderedProduct.getProductCount());
        return true;
    }
}
