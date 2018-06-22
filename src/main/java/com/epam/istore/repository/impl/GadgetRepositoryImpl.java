package com.epam.istore.repository.impl;


import com.epam.istore.connection.ConnectionHolder;
import com.epam.istore.entity.Category;
import com.epam.istore.entity.Product;
import com.epam.istore.entity.ProducerCountry;
import com.epam.istore.entity.User;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.repository.GadgetRepository;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private final static Logger LOGGER = Logger.getRootLogger();
    private final static String GET_ALL_COUNTRIES = "SELECT id,country_name FROM producer_country;";
    private ConnectionHolder connectionHolder;


    public GadgetRepositoryImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public List<Product> getFiltered(String query) throws RepositoryException {
        List<Product> listOfProducts = new ArrayList<>();
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = createGadget(resultSet);
                listOfProducts.add(product);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return listOfProducts;
    }

    public Product getProductById(int productId) throws RepositoryException {
        Product product = null;
        ResultSet resultSet = null;
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID);) {
            int k = 1;
            preparedStatement.setInt(k++, productId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = createGadget(resultSet);
                return product;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return product;
    }

    @Override
    public List<ProducerCountry> getAllCountries() throws RepositoryException {
        List<ProducerCountry> listOfProductCountries = new ArrayList<>();
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COUNTRIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProducerCountry producerCountry = createProducerCountry(resultSet);
                listOfProductCountries.add(producerCountry);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return listOfProductCountries;
    }


    @Override
    public List<Category> getAllCategories() throws RepositoryException {
        List<Category> categories = new ArrayList<>();
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CATEGORIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = createCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RepositoryException(e);
        }
        return categories;
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
