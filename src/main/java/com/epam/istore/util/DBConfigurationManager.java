package com.epam.istore.util;

import java.util.ResourceBundle;


public class DBConfigurationManager {
    public static final String DATABASE_DRIVER_NAME = "driver_name";
    public static final String DATABASE_CONNECTION_URL = "url";
    public static final String DATABASE_USERNAME = "username";
    public static final String DATABASE_PASSWORD = "password";
    private static final String BUNDLE_NAME = "database";
    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public String getString(String key) {
        return bundle.getString(key);
    }
}
