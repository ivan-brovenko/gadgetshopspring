package com.epam.istore.repository;


import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.exception.RepositoryException;

import java.util.List;

public interface GadgetRepository {
    List<Product> getFiltered(String query) throws RepositoryException;

    List<Category> getAllCategories() throws RepositoryException;

    int getNumberOfRows(String query) throws RepositoryException;

    List<ProducerCountry> getAllCountries() throws RepositoryException;

    Product getProductById(int productId) throws RepositoryException;

}
