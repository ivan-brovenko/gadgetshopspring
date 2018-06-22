package com.epam.istore.repository.impl;

import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.Order;
import com.epam.istore.entity.OrderedProduct;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.repository.OrderRepository;
import org.apache.log4j.Logger;

import java.sql.*;


public class OrderRepositoryImpl implements OrderRepository {
    private final static String ADD_ORDER = "INSERT INTO gadget_shop.order VALUES (DEFAULT ,DEFAULT ,DEFAULT ,?,?,?,?,?,?)";
    private final static String ADD_ITEM = "INSERT INTO gadget_shop.order_gadget VALUES (? ,?,?,?)";
    private final static Logger LOGGER = Logger.getRootLogger();
    private static final String NO_SUCH_BILLING = "No Such Billing";
    private static final String NO_SUCH_SHIPPING = "No Such Shipping";
    public static final String ID = "id";
    private ConnectionHolder connectionHolder;
    private final static String GET_SHIP_ID_BY_NAME = "SELECT ship.id FROM ship WHERE name = ?";
    private final static String GET_BILL_ID_BY_NAME = "SELECT  bill.id FROM bill WHERE bill.name = ?";

    public OrderRepositoryImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public boolean addOrder(Order order) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
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
                for (OrderedProduct orderedProduct : order.getListOfOrderedProducts()) {
                    addItem(orderId, orderedProduct);
                }
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
    }

    private int getShipIdByName(String shippingName) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_SHIP_ID_BY_NAME)) {
            preparedStatement.setString(1, shippingName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(ID);
            }
            throw new RepositoryException(NO_SUCH_SHIPPING);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
    }

    private int getBillIdByName(String billingName) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BILL_ID_BY_NAME)) {
            preparedStatement.setString(1, billingName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            throw new RepositoryException(NO_SUCH_BILLING);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
    }

    private boolean addItem(int orderId, OrderedProduct orderedProduct) throws RepositoryException {
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ITEM)) {
            int k = 1;
            preparedStatement.setInt(k++, orderId);
            preparedStatement.setInt(k++, orderedProduct.getProductId());
            preparedStatement.setDouble(k++, orderedProduct.getOneProductPrice());
            preparedStatement.setInt(k++, orderedProduct.getProductCount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return true;
    }
}
