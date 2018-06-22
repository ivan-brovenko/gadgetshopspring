package com.epam.istore.connection;

import com.epam.istore.exception.ConnectionPoolException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @Before
    public void setUp() throws ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
    }

    @Test
    public void testConnectionPoolOnReturnConnection() {
        assertTrue(connectionPool.getConnection() instanceof Connection);
    }
}