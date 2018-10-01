package com.epam.istore.resources.impl;


import com.epam.istore.model.ProductDto;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.model.Category;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.model.Product;
import com.epam.istore.messages.Messages;
import com.epam.istore.resources.ProductResource;
import com.epam.istore.service.ProductService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.LIMIT_ARGUMENT;
import static com.epam.istore.util.StringConstants.CURRENT_PAGE_EQUALS;
import static com.epam.istore.util.StringConstants.URL;

@Controller
@Data
public class ProductResourceImpl implements ProductResource {
    public final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    private ProductService productService;

    @Override
    public String createProductPage(HttpServletRequest request, ProductDto productDto) {
//        setAttributesFromPreviousRequest(request);
        fill(request, productDto);
        ProductListDTO productListDTO = productService.getProductListDTO(productDto);
        int numberOfPages = productListDTO.getNumberOfPages();
        List<Product> products = productListDTO.getProducts();
        request.setAttribute(GADGETS, products);
        List<Category> categories = productService.getAllCategories();
        request.setAttribute(CATEGORIES, categories);
        List<ProducerCountry> producerCountries = productService.getAllCountries();
        String urlListOfAttributes = validateQueryString(request.getQueryString());
        setAttributesToRequest(request, productDto, numberOfPages, producerCountries, urlListOfAttributes);
        return PAGES_PRODUCTS_JSP;
    }

    private String validateQueryString(String queryString) {
        if (queryString != null && queryString.contains(CURRENT_PAGE_EQUALS)) {
            return queryString.replaceAll(CURRENT_PAGE, StringUtils.EMPTY);
        }
        return queryString;
    }

    private void setAttributesToRequest(HttpServletRequest request, ProductDto productDto, int numberOfPages, List<ProducerCountry> producerCountries, String urlListOfAttributes) {
        request.setAttribute(COUNTRIES, producerCountries);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(Messages.CURRENT_PAGE, productDto.getCurrentPage());
        request.setAttribute(RECORDS_PER_PAGE, productDto.getProductLimit());
        request.setAttribute(URL, urlListOfAttributes);
    }

    private ProductDto fill(HttpServletRequest request, ProductDto productDto) {
        productDto.setPriceMin(request.getParameter(PRICE_FROM));
        productDto.setPriceMax(request.getParameter(PRICE_TO));
        productDto.setCategory(filterCategories(request.getParameterValues(CATEGORIES_CHECK)));
        productDto.setProductCountry(request.getParameter(PRODUCER_COUNTRY));
        productDto.setProductName(request.getParameter(PRODUCT_NAME));
        productDto.setSortingType(request.getParameter(SORT));
        if (StringUtils.isNotBlank(request.getParameter(LIMIT_ARGUMENT)) &&
                StringUtils.isNumeric(request.getParameter(LIMIT_ARGUMENT))) {
            productDto.setProductLimit(request.getParameter(LIMIT_ARGUMENT));
        } else {
            productDto.setProductLimit(DEFAULT_PRODUCT_LIMIT);
        }
        if (StringUtils.isNotBlank(request.getParameter(Messages.CURRENT_PAGE)) &&
                StringUtils.isNumeric(request.getParameter(Messages.CURRENT_PAGE))) {
            productDto.setCurrentPage(request.getParameter(Messages.CURRENT_PAGE));
        } else {
            productDto.setCurrentPage(DEFAULT_CURRENT_PAGE_VALUE);
        }
        return productDto;
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
