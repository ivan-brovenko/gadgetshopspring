package com.epam.istore.service.impl;


import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.builder.ProductQueryBuilder;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.entity.Category;
import com.epam.istore.entity.Product;
import com.epam.istore.entity.ProducerCountry;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.repository.impl.GadgetRepositoryImpl;
import com.epam.istore.service.GadgetService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class GadgetServiceImpl implements GadgetService {

    @Autowired
    private GadgetRepositoryImpl gadgetRepository;
    private ProductQueryBuilder productQueryBuilder = new ProductQueryBuilder();

    @Transactional
    @Override
    public List<Product> getFiltered(ProductFormBean productFormBean) throws ServiceException {
        try {
            String query = productQueryBuilder.limitFilter(productFormBean,productQueryBuilder.build(productFormBean));
            return gadgetRepository.getFiltered(query);
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public int getNumberOfRows(String query) throws ServiceException {
        try {
            return gadgetRepository.getNumberOfRows(query);
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<ProducerCountry> getAllCountries() throws ServiceException {
        try {
            return gadgetRepository.getAllCountries();
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<Category> getAllCategories() throws ServiceException {
        try {
            return gadgetRepository.getAllCategories();
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfPages(ProductFormBean productFormBean) throws ServiceException {
        String unfiltered = productQueryBuilder.build(productFormBean);
        double recordsPrePage = Integer.parseInt(productFormBean.getProductLimit());
        double rows = getNumberOfRows(unfiltered);
        return (int) (Math.ceil(rows / recordsPrePage));
    }

    @Override
    public ProductListDTO getProductListDTO(ProductFormBean productFormBean) throws ServiceException {
        ProductListDTO productListDTO = new ProductListDTO();
        int numberOfPages = getNumberOfPages(productFormBean);
        if (numberOfPages!=0 && numberOfPages >= Integer.parseInt(productFormBean.getCurrentPage())){
            productListDTO.setNumberOfPages(numberOfPages);
            productListDTO.setProducts(getFiltered(productFormBean));
        } else {
            productListDTO.setProducts(new ArrayList<>());
            productListDTO.setNumberOfPages(1);
        }
        return productListDTO;
    }

    @Transactional
    @Override
    public Product getProductById(int productId) throws ServiceException {
        try {
            return gadgetRepository.getProductById(productId);
        } catch (RepositoryException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

}
