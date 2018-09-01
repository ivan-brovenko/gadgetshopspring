package com.epam.istore.service;


import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.exception.ServiceException;

import java.util.List;

public interface GadgetService {
    List<Product> getFiltered(ProductFormBean productFormBean) throws ServiceException;

    int getNumberOfRows(String query) throws ServiceException;

    List<ProducerCountry> getAllCountries() throws ServiceException;

    List<Category> getAllCategories() throws ServiceException;

    int getNumberOfPages(ProductFormBean productFormBean) throws ServiceException;

    ProductListDTO getProductListDTO(ProductFormBean productFormBean) throws ServiceException;

    Product getProductById(int productId) throws ServiceException;

}
