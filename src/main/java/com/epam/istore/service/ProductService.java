package com.epam.istore.service;


import com.epam.istore.model.ProductDto;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;

import java.util.List;

public interface ProductService {
    List<Product> getFiltered(ProductDto productDto);

    int getNumberOfRows(String query);

    List<ProducerCountry> getAllCountries();

    List<Category> getAllCategories();

    int getNumberOfPages(ProductDto productDto);

    ProductListDTO getProductListDTO(ProductDto productDto);

    Product getProductById(int productId);

}
