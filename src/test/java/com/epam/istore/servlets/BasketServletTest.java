package com.epam.istore.servlets;

import com.epam.istore.cart.Cart;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.Product;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.service.GadgetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketServletTest {
    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT_COUNT = "productCount";
    private static final String PRODUCT_ID_TEST_VALUE_STRING = "11";
    private static final String PRODUCT_COUNT_TEST_VALUE_STRING = "100";
    private static final String CART = "cart";
    private static final int PRODUCT_ID_VALUE_INTEGER = 11;
    private static final int PRODUCT_COUNT_VALUE_INTEGER = 100;
    private static final String BASKET_TOTAL_PRICE = "basketTotalPrice";
    private static final double BASKET_TOTAL_PRICE_VALUE = 1000.0;
    private static final String PAGES_BASKET_JSP = "/pages/basket.jsp";
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Cart cart;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private GadgetService gadgetService;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;
    @Mock
    private ServletContext servletContext;
    @InjectMocks
    private BasketServlet basketServlet;

    @Before
    public void setUp() throws ServiceException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(PRODUCT_ID_TEST_VALUE_STRING);
        when(request.getParameter(PRODUCT_COUNT)).thenReturn(PRODUCT_COUNT_TEST_VALUE_STRING);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART)).thenReturn(cart);
        when(gadgetService.getProductById(PRODUCT_ID_VALUE_INTEGER)).thenReturn(product);
        when(cart.getTotalPrice()).thenReturn(BASKET_TOTAL_PRICE_VALUE);
        when(request.getRequestDispatcher(PAGES_BASKET_JSP)).thenReturn(requestDispatcher);
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void testDoPostOnReplacingElementInTheCart() throws ServletException, IOException {
        basketServlet.doPost(request, response);
        verify(cart).set(PRODUCT_ID_VALUE_INTEGER, PRODUCT_COUNT_VALUE_INTEGER);
    }

    @Test
    public void testDoPutOnAddingElementToTheCart() throws ServletException, IOException {
        basketServlet.doPut(request, response);
        verify(cart).addToCart(product, PRODUCT_COUNT_VALUE_INTEGER);
    }

    @Test
    public void testDoDeleteOnClearingCart() throws ServletException, IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn("");
        basketServlet.doDelete(request, response);
        verify(cart).clear();
    }

    @Test
    public void testDoDeleteOnRemovingFromCartById() throws ServletException, IOException {
        basketServlet.doDelete(request, response);
        verify(cart).removeFromCart(PRODUCT_ID_VALUE_INTEGER);
    }

    @Test
    public void testDoGetOnSettingTotalPrice() throws ServletException, IOException {
        basketServlet.doGet(request, response);
        verify(cart).getTotalPrice();
        verify(request).setAttribute(BASKET_TOTAL_PRICE, BASKET_TOTAL_PRICE_VALUE);
    }
}