package com.epam.istore.util;


public enum Genders {
    MALE(true),FEMALE(false);
    public boolean value;

    Genders(boolean value) {
        this.value = value;
    }

    public boolean isMale() {
        return value;
    }
}
