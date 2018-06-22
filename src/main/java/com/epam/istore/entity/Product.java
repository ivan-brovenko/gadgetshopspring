package com.epam.istore.entity;


public class Product {
    private Category category;
    private String name;
    private double price;
    private int memorySize;
    private ProducerCountry producerCountry;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public ProducerCountry getProducerCountry() {
        return producerCountry;
    }

    public void setProducerCountry(ProducerCountry producerCountry) {
        this.producerCountry = producerCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
