package com.epam.istore.service.impl;


import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.builder.ProductQueryBuilder;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.repository.impl.GadgetRepositoryImpl;
import com.epam.istore.service.GadgetService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@Transactional
public class GadgetServiceImpl implements GadgetService {

    @Autowired
    private GadgetRepositoryImpl gadgetRepository;
    private ProductQueryBuilder productQueryBuilder = new ProductQueryBuilder();

    @Override
    public List<Product> getFiltered(ProductFormBean productFormBean) {
        String query = productQueryBuilder.limitFilter(productFormBean, productQueryBuilder.build(productFormBean));
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
    public int getNumberOfPages(ProductFormBean productFormBean) {
        String unfiltered = productQueryBuilder.build(productFormBean);
        double recordsPrePage = Integer.parseInt(productFormBean.getProductLimit());
        double rows = getNumberOfRows(unfiltered);
        return (int) (Math.ceil(rows / recordsPrePage));
    }

    @Override
    public ProductListDTO getProductListDTO(ProductFormBean productFormBean) {
        ProductListDTO productListDTO = new ProductListDTO();
        int numberOfPages = getNumberOfPages(productFormBean);
        if (numberOfPages != 0 && numberOfPages >= Integer.parseInt(productFormBean.getCurrentPage())) {
            productListDTO.setNumberOfPages(numberOfPages);
            productListDTO.setProducts(getFiltered(productFormBean));
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
