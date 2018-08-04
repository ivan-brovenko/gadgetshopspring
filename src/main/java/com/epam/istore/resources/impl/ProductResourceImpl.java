package com.epam.istore.resources.impl;


import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.entity.Category;
import com.epam.istore.entity.ProducerCountry;
import com.epam.istore.entity.Product;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.messages.Messages;
import com.epam.istore.resources.ProductResource;
import com.epam.istore.service.GadgetService;
import com.epam.istore.util.StringConstants;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.LIMIT_ARGUMENT;
import static com.epam.istore.util.StringConstants.CURRENT_PAGE_EQUALS;
import static com.epam.istore.util.StringConstants.URL;

@Controller
public class ProductResourceImpl implements ProductResource {
    public final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    @Setter
    private GadgetService gadgetService;

    @Autowired
    @Setter
    private ProductFormBean productFormBean;

    @Override
    public String createProductPage(HttpServletRequest request) {
        try {
            setAttributesFromPreviousRequest(request);
            fill(request);
            ProductListDTO productListDTO = gadgetService.getProductListDTO(productFormBean);
            int numberOfPages = productListDTO.getNumberOfPages();
            List<Product> products = productListDTO.getProducts();
            request.setAttribute(GADGETS, products);
            List<Category> categories = gadgetService.getAllCategories();
            request.setAttribute(CATEGORIES, categories);
            List<ProducerCountry> producerCountries = gadgetService.getAllCountries();
            String urlListOfAttributes = validateQueryString(request.getQueryString());
            setAttributesToRequest(request, productFormBean, numberOfPages, producerCountries, urlListOfAttributes);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return PAGES_PRODUCTS_JSP;
    }

    private String validateQueryString(String queryString) {
        if (queryString != null && queryString.contains(CURRENT_PAGE_EQUALS)) {
            return queryString.replaceAll(CURRENT_PAGE, StringUtils.EMPTY);
        }
        return queryString;
    }

    private void setAttributesToRequest(HttpServletRequest request, ProductFormBean productFormBean, int numberOfPages, List<ProducerCountry> producerCountries, String urlListOfAttributes) {
        request.setAttribute(COUNTRIES, producerCountries);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(Messages.CURRENT_PAGE, productFormBean.getCurrentPage());
        request.setAttribute(RECORDS_PER_PAGE, productFormBean.getProductLimit());
        request.setAttribute(URL, urlListOfAttributes);
    }

    private ProductFormBean fill(HttpServletRequest request) {

        productFormBean.setPriceMin(request.getParameter(PRICE_FROM));
        productFormBean.setPriceMax(request.getParameter(PRICE_TO));
        productFormBean.setCategory(filterCategories(request.getParameterValues(CATEGORIES_CHECK)));
        productFormBean.setProductCountry(request.getParameter(PRODUCER_COUNTRY));
        productFormBean.setProductName(request.getParameter(PRODUCT_NAME));
        productFormBean.setSortingType(request.getParameter(SORT));
        if (StringUtils.isNotBlank(request.getParameter(LIMIT_ARGUMENT)) &&
                StringUtils.isNumeric(request.getParameter(LIMIT_ARGUMENT))) {
            productFormBean.setProductLimit(request.getParameter(LIMIT_ARGUMENT));
        } else {
            productFormBean.setProductLimit(DEFAULT_PRODUCT_LIMIT);
        }
        if (StringUtils.isNotBlank(request.getParameter(Messages.CURRENT_PAGE)) &&
                StringUtils.isNumeric(request.getParameter(Messages.CURRENT_PAGE))) {
            productFormBean.setCurrentPage(request.getParameter(Messages.CURRENT_PAGE));
        } else {
            productFormBean.setCurrentPage(DEFAULT_CURRENT_PAGE_VALUE);
        }
        return productFormBean;
    }

    private String[] filterCategories(String[] unfiltered) {
        if (unfiltered == null) {
            return new String[0];
        }
        int counter = (int) Arrays.stream(unfiltered).filter(Objects::nonNull).count();
        return Arrays.copyOfRange(unfiltered, 0, counter);
    }

    private void setAttributesFromPreviousRequest(HttpServletRequest request) {
        request.setAttribute(PRICE_FROM, request.getParameter(PRICE_FROM));
        request.setAttribute(PRICE_TO, request.getParameter(PRICE_TO));
        request.setAttribute(CATEGORIES_CHECK, request.getParameterValues(CATEGORIES_CHECK));
        request.setAttribute(PRODUCER_COUNTRY, request.getParameter(PRODUCER_COUNTRY));
        request.setAttribute(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        request.setAttribute(SORT, request.getParameter(SORT));
        request.setAttribute(LIMIT_ARGUMENT, request.getParameter(LIMIT_ARGUMENT));
    }
}
