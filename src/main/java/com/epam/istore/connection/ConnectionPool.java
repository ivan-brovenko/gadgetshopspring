package com.epam.istore.connection;


import com.epam.istore.exception.ConnectionPoolException;
import com.epam.istore.util.DBConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {

    private static ConnectionPool instance = null;
    private static final int POOL_SIZE = 10;
    private static final int MAX_TIME = 2;
    private static Lock lock = new ReentrantLock(true);
    private BlockingQueue<Connection> connections;
    private final static Logger LOGGER = Logger.getRootLogger();
    private DBConfigurationManager conf = new DBConfigurationManager();
    private final String CONNECTION = conf.getString(DBConfigurationManager.DATABASE_CONNECTION_URL);
    private final String USERNAME = conf.getString(DBConfigurationManager.DATABASE_USERNAME);
    private final String PASSWORD = conf.getString(DBConfigurationManager.DATABASE_PASSWORD);
    private final String DRIVER = conf.getString(DBConfigurationManager.DATABASE_DRIVER_NAME);

    private ConnectionPool() throws ConnectionPoolException {
        initConnection();
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        lock.lock();

        if (null == instance) {
            instance = new ConnectionPool();
        }
        lock.unlock();

        return instance;
    }

    private void initConnection() throws ConnectionPoolException {
        connections = new LinkedBlockingDeque<>(POOL_SIZE);

        try {
            Class.forName(DRIVER);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
                connections.add(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public final Connection getConnection() {
        try {
            Connection connection = connections.poll(MAX_TIME, TimeUnit.SECONDS);
            if (connection != null) {
                LOGGER.info("Connection " + connection + " took from connection pool");
            } else {
                LOGGER.error("Couldn't retrieve a connection from pool");
            }
            return connection;
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public void release(Connection connection) {
        if (connection != null) {
            try {
                connections.put(connection);
                LOGGER.info("Connection " + connection + "  released");
                LOGGER.info("There are(is) " + (connections.size() - connections.remainingCapacity()) + " connection(s) in the pool.");
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void shutDown() {
        Iterator<Connection> iterator = connections.iterator();

        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            try {
                connection.close();
                iterator.remove();
            } catch (SQLException e) {
                LOGGER.error("Couldn't close connection: " + e.getMessage());
            }
        }
        LOGGER.info("Connection pool is shut down");
    }
}
