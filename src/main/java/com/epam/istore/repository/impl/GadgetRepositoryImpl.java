package com.epam.istore.repository.impl;


import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.Category;
import com.epam.istore.entity.Product;
import com.epam.istore.entity.ProducerCountry;
import com.epam.istore.entity.User;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.repository.GadgetRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GadgetRepositoryImpl implements GadgetRepository {
    private static final String ID = "id";
    private static final String MEMORY_SIZE = "memory_size";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "category_name";
    private static final String PRODUCER_COUNTRY_ID = "producer_country_id";
    private static final String COUNTRY_NAME = "country_name";
    private final static String GET_PRODUCT_BY_ID = "SELECT gadget.id,gadget.memory_size,gadget.name,gadget.price,gadget.category_id,category.category_name," +
            " gadget.producer_country_id,producer_country.country_name FROM gadget JOIN category " +
            " ON gadget.category_id = category.id JOIN producer_country ON gadget.producer_country_id = producer_country.id" +
            " WHERE gadget.id = ?";
    private final static String GET_ALL_CATEGORIES = "SELECT id,category_name FROM category;";
    private final static String GET_ALL_COUNTRIES = "SELECT id,country_name FROM producer_country;";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GadgetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getFiltered(String query) throws RepositoryException {
        return jdbcTemplate.query(query, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return createGadget(resultSet);
            }
        });
    }

    public Product getProductById(int productId) throws RepositoryException {
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID, new Object[]{productId}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return createGadget(resultSet);
            }
        });
    }

    @Override
    public List<ProducerCountry> getAllCountries() throws RepositoryException {
        return jdbcTemplate.query(GET_ALL_COUNTRIES, new RowMapper<ProducerCountry>() {
            @Override
            public ProducerCountry mapRow(ResultSet resultSet, int i) throws SQLException {
                return createProducerCountry(resultSet);
            }
        });
    }


    @Override
    public List<Category> getAllCategories() throws RepositoryException {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return createCategory(resultSet);
            }
        });
    }

    @Override
    public int getNumberOfRows(String query) throws RepositoryException {
        return getFiltered(query).size();
    }

    private ProducerCountry createProducerCountry(ResultSet resultSet) throws SQLException {
        ProducerCountry producerCountry = new ProducerCountry();
        producerCountry.setId(resultSet.getInt(ID));
        producerCountry.setCountryName(resultSet.getString(COUNTRY_NAME));
        return producerCountry;
    }

    private Category createCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt(ID));
        category.setCategoryName(resultSet.getString(CATEGORY_NAME));
        return category;
    }


    private Product createGadget(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Category category = new Category();
        ProducerCountry producerCountry = new ProducerCountry();
        product.setId(resultSet.getInt(ID));
        product.setMemorySize(resultSet.getInt(MEMORY_SIZE));
        product.setName(resultSet.getString(NAME));
        product.setPrice(resultSet.getDouble(PRICE));
        category.setId(resultSet.getInt(CATEGORY_ID));
        category.setCategoryName(resultSet.getString(CATEGORY_NAME));
        product.setCategory(category);
        producerCountry.setId(resultSet.getInt(PRODUCER_COUNTRY_ID));
        producerCountry.setCountryName(resultSet.getString(COUNTRY_NAME));
        product.setProducerCountry(producerCountry);
        return product;
    }
}
