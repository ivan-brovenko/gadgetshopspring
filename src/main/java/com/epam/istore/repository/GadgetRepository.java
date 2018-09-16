package com.epam.istore.repository;


import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;

import java.util.List;

public interface GadgetRepository {
    List<Product> getFiltered(String query);

    List<Category> getAllCategories();

    int getNumberOfRows(String query);

    List<ProducerCountry> getAllCountries();

    Product getProductById(int productId);

}
