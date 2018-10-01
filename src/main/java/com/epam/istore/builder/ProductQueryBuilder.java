package com.epam.istore.builder;

import com.epam.istore.model.ProductDto;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.istore.messages.Messages.*;


public class ProductQueryBuilder {
    private static final String START_QUERY = "SELECT gadget.id,gadget.memory_size,gadget.name," +
            "gadget.price,gadget.category_id,category.category_name," +
            "gadget.producer_country_id,producer_country.country_name FROM gadget" +
            " JOIN category" +
            " ON gadget.category_id =" +
            " category.id JOIN producer_country ON gadget.producer_country_id =" +
            " producer_country.id";
    private static final String WHERE_GADGET_PRICE = " WHERE gadget.price >=";
    private static final String AND_GADGET_PRICE = " AND gadget.price <=";
    private static final String AND_CATEGORY_CATEGORY_NAME_IN = " AND category.category_name in (";
    private static final String AND_PRODUCER_COUNTRY_COUNTRY_NAME = " AND producer_country.country_name = '";
    private static final String AND_GADGET_NAME_LIKE = " AND gadget.name LIKE '";
    private static final String ORDER_BY_GADGET_NAME = " ORDER BY gadget.name";
    private static final String ORDER_BY_GADGET_NAME_DESC = " ORDER BY gadget.name DESC";
    private static final String ORDER_BY_GADGET_PRICE = " ORDER BY gadget.price";
    private static final String ORDER_BY_GADGET_PRICE_DESC = " ORDER BY gadget.price DESC";
    private static final String NAME_ASC = "nAZ";
    private static final String NAME_DESC = "nZA";
    private static final String PRICE_ASC = "p0N";
    private static final String PRICE_DESC = "pN0";
    private StringBuilder start = new StringBuilder(START_QUERY);
    private Map<String, String> sortingContainer = new HashMap<>();

    public ProductQueryBuilder() {
        sortingContainer.put(NAME_ASC, ORDER_BY_GADGET_NAME);
        sortingContainer.put(NAME_DESC, ORDER_BY_GADGET_NAME_DESC);
        sortingContainer.put(PRICE_ASC, ORDER_BY_GADGET_PRICE);
        sortingContainer.put(PRICE_DESC, ORDER_BY_GADGET_PRICE_DESC);
    }

    private void priceFilter(ProductDto productDto) {
        if (checkNotNullAndNotEmpty(productDto.getPriceMin())) {
            start.append(WHERE_GADGET_PRICE + productDto.getPriceMin());
        }
        if (checkNotNullAndNotEmpty(productDto.getPriceMax())) {
            start.append(AND_GADGET_PRICE + productDto.getPriceMax());
        }
    }

    private void categoryFilter(ProductDto productDto) {
        if (productDto.getCategory() != null && productDto.getCategory().length > 0) {
            start.append(AND_CATEGORY_CATEGORY_NAME_IN + parseArray(productDto.getCategory()) + ")");
        }
    }

    private void productCountryFilter(ProductDto productDto) {
        if (checkNotNullAndNotEmpty(productDto.getProductCountry())) {
            start.append(AND_PRODUCER_COUNTRY_COUNTRY_NAME + productDto.getProductCountry() + "'");
        }
    }

    private void searchByNameFilter(ProductDto productDto) {
        if (validateName(productDto.getProductName())) {
            start.append(AND_GADGET_NAME_LIKE + productDto.getProductName() + "%'");
        }
    }

    private void sortByName(ProductDto productDto) {
        if (!checkNotNullAndNotEmpty(productDto.getSortingType())) {
            return;
        }
        if (sortingContainer.containsKey(productDto.getSortingType())) {
            start.append(sortingContainer.get(productDto.getSortingType()));
        }
    }

    private String parseArray(String[] arrayToParse) {
        StringJoiner stringJoiner = new StringJoiner(COMMA);
        for (String s : arrayToParse) {
            stringJoiner.add(STRING_QUOTE + s + STRING_QUOTE);
        }
        return stringJoiner.toString();
    }

    private boolean checkNotNullAndNotEmpty(String stringToCheck) {
        return stringToCheck != null && !stringToCheck.equals(StringUtils.EMPTY);
    }

    private boolean validateName(String nameParameter) {
        if (checkNotNullAndNotEmpty(nameParameter) && nameParameter.contains(";")) {
            return false;
        }
        return checkNotNullAndNotEmpty(nameParameter);
    }

    private boolean isNumeric(String stringToCheck) {
        return StringUtils.isNumeric(stringToCheck);
    }

    public String limitFilter(ProductDto productDto, String start) {
        if (productDto.getProductLimit() != null &&
                !productDto.getProductLimit().equals(StringUtils.EMPTY) &&
                productDto.getCurrentPage() != null &&
                isNumeric(productDto.getCurrentPage()) &&
                isNumeric(productDto.getProductLimit())) {
            int currentPage = Integer.parseInt(productDto.getCurrentPage());
            int recordsPrePage = Integer.parseInt(productDto.getProductLimit());
            int offset = currentPage * recordsPrePage - recordsPrePage;
            return start + LIMIT + recordsPrePage + OFFSET + offset;
        }
        return StringUtils.EMPTY;
    }

    public String build(ProductDto productDto) {
        start = new StringBuilder(START_QUERY);
        priceFilter(productDto);
        categoryFilter(productDto);
        productCountryFilter(productDto);
        searchByNameFilter(productDto);
        sortByName(productDto);
        return start.toString();
    }
}
