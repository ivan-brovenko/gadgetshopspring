package com.epam.istore.service.impl;


import com.epam.istore.model.ProductDto;
import com.epam.istore.builder.ProductQueryBuilder;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.dao.impl.ProductRepositoryImpl;
import com.epam.istore.service.ProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositoryImpl gadgetRepository;
    private ProductQueryBuilder productQueryBuilder = new ProductQueryBuilder();

    @Override
    public List<Product> getFiltered(ProductDto productDto) {
        String query = productQueryBuilder.limitFilter(productDto, productQueryBuilder.build(productDto));
        return gadgetRepository.getFiltered(query);
    }

    @Override
    public int getNumberOfRows(String query) {
        return gadgetRepository.getNumberOfRows(query);
    }

    @Override
    public List<ProducerCountry> getAllCountries() {
        return gadgetRepository.getAllCountries();
    }

    @Override
    public List<Category> getAllCategories() {
        return gadgetRepository.getAllCategories();
    }

    @Override
    public int getNumberOfPages(ProductDto productDto) {
        String unfiltered = productQueryBuilder.build(productDto);
        double recordsPrePage = Integer.parseInt(productDto.getProductLimit());
        double rows = getNumberOfRows(unfiltered);
        return (int) (Math.ceil(rows / recordsPrePage));
    }

    @Override
    public ProductListDTO getProductListDTO(ProductDto productDto) {
        ProductListDTO productListDTO = new ProductListDTO();
        int numberOfPages = getNumberOfPages(productDto);
        if (numberOfPages != 0 && numberOfPages >= Integer.parseInt(productDto.getCurrentPage())) {
            productListDTO.setNumberOfPages(numberOfPages);
            productListDTO.setProducts(getFiltered(productDto));
        } else {
            productListDTO.setProducts(new ArrayList<>());
            productListDTO.setNumberOfPages(1);
        }
        return productListDTO;
    }

    @Override
    public Product getProductById(int productId){
        return gadgetRepository.getProductById(productId);
    }

}
