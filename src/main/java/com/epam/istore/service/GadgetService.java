package com.epam.istore.service;


import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;

import java.util.List;

public interface GadgetService {
    List<Product> getFiltered(ProductFormBean productFormBean);

    int getNumberOfRows(String query);

    List<ProducerCountry> getAllCountries();

    List<Category> getAllCategories();

    int getNumberOfPages(ProductFormBean productFormBean);

    ProductListDTO getProductListDTO(ProductFormBean productFormBean);

    Product getProductById(int productId);

}
