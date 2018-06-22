package com.epam.istore.transaction;


import com.epam.istore.exception.RepositoryException;
import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private ConnectionPool connectionPool;
    private ConnectionHolder connectionHolder;
    private final static Logger LOGGER = Logger.getRootLogger();

    public TransactionManager(ConnectionPool connectionPool, ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
        this.connectionPool = connectionPool;
    }

    public <T> T doInTransaction(CustomSupplier<T> supplier) throws RepositoryException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connectionHolder.setConnection(connection);
            connection.setAutoCommit(false);
            T result = supplier.get();
            connection.commit();
            return result;
        } catch (SQLException e) {
            rollback(connection);
            throw new RepositoryException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error(e);
            }
            connectionPool.release(connection);
        }
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }
}
