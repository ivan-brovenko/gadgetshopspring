package com.epam.istore.builder;

import com.epam.istore.bean.ProductFormBean;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductQueryBuilderTest {
    private final static String DEFAULT_STRING = "SELECT gadget.id,gadget.memory_size," +
            "gadget.name,gadget.price,gadget.category_id,category.category_name," +
            "gadget.producer_country_id,producer_country.country_name FROM gadget" +
            " JOIN category ON gadget.category_id = category.id JOIN producer_country" +
            " ON gadget.producer_country_id = producer_country.id";
    private final static String PRICE_MIN_1000 = " WHERE gadget.price >=1000";
    private final static String LIMIT_CONDITION_0_6 = " LIMIT 6 OFFSET 0";
    @Mock
    private ProductFormBean productFormBean;
    @InjectMocks
    private ProductQueryBuilder productQueryBuilder;

    @Test
    public void testBuilderOnReturnDefaultStringIfBeanDataIsEmpty() {
        assertEquals(DEFAULT_STRING, productQueryBuilder.build(productFormBean));
    }

    @Test
    public void testBuilderOnAddingMinPriceCondition() {
        when(productFormBean.getPriceMin()).thenReturn("1000");
        assertEquals(DEFAULT_STRING + PRICE_MIN_1000, productQueryBuilder.build(productFormBean));
    }

    @Test
    public void testBuilderLimitOnReturnLimitConditionFor1PageAnd6ProductsPerPage() {
        when(productFormBean.getProductLimit()).thenReturn("6");
        when(productFormBean.getCurrentPage()).thenReturn("1");
        assertEquals(DEFAULT_STRING + LIMIT_CONDITION_0_6, productQueryBuilder.limitFilter(productFormBean, DEFAULT_STRING));
    }

    @Test
    public void testBuilderOnReturnEmptyStringIfBeanEmpty() {
        assertEquals(StringUtils.EMPTY, productQueryBuilder.limitFilter(productFormBean, DEFAULT_STRING));
    }
}