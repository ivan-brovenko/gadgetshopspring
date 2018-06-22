package com.epam.istore.util;

import java.util.Random;


public class RandomStringGenerator {
    private static final int MAX_STRING_LENGTH = 10;
    private final static String SALT_CHAR_NUMBERS = "1234567890";

    public String getSaltString() {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < MAX_STRING_LENGTH) {
            int index = (int) (rnd.nextFloat() * SALT_CHAR_NUMBERS.length());
            salt.append(SALT_CHAR_NUMBERS.charAt(index));
        }
        return salt.toString();
    }
}
