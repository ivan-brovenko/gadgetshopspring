package com.epam.istore.model;


public class Category {
    private String categoryName;
    private int id;

    public void setId(int id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public int getId() {
        return id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
