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
import org.apache.log4j.Logger;
import com.epam.istore.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;


public class GadgetServiceImpl implements GadgetService {
    private GadgetRepositoryImpl gadgetRepository;
    private TransactionManager transactionManager;
    private ProductQueryBuilder productQueryBuilder = new ProductQueryBuilder();
    private final static Logger LOGGER = Logger.getRootLogger();

    public GadgetServiceImpl(GadgetRepositoryImpl gadgetRepository, TransactionManager transactionManager) {
        this.gadgetRepository = gadgetRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Product> getFiltered(ProductFormBean productFormBean) throws ServiceException {
        try {
            String query = productQueryBuilder.limitFilter(productFormBean,productQueryBuilder.build(productFormBean));
            return transactionManager.doInTransaction(()->gadgetRepository.getFiltered(query));
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRows(String query) throws ServiceException {
        try {
            return transactionManager.doInTransaction(()->gadgetRepository.getNumberOfRows(query));
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProducerCountry> getAllCountries() throws ServiceException {
        try {
            return transactionManager.doInTransaction(()->gadgetRepository.getAllCountries());
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() throws ServiceException {
        try {
            return transactionManager.doInTransaction(()->gadgetRepository.getAllCategories());
        } catch (RepositoryException e) {
            LOGGER.error(e);
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

    @Override
    public Product getProductById(int productId) throws ServiceException {
        try {
            return transactionManager.doInTransaction(()->gadgetRepository.getProductById(productId));
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
    }

}
