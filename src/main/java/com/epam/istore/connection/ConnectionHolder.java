package com.epam.istore.connection;

import java.sql.Connection;

public class ConnectionHolder {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getConnection() {
        return threadLocal.get();
    }

    public void setConnection(Connection connection) {
        threadLocal.set(connection);
    }
}
