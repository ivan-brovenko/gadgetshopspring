package com.epam.istore.servlets;

import com.epam.istore.bean.OrderBean;
import com.epam.istore.bean.ProductFormBean;
import com.epam.istore.converter.impl.OrderConverter;
import com.epam.istore.dto.ProductListDTO;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.service.GadgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.epam.istore.messages.Messages.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private GadgetService gadgetService;
    @Mock
    private ProductListDTO productListDTO;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductFormBean productFormBean;
    @InjectMocks
    private ProductServlet productServlet;

    @Before
    public void setUp() throws ServiceException {
        when(request.getSession()).thenReturn(session);
        when(gadgetService.getProductListDTO(productFormBean)).thenReturn(productListDTO);
        when(productListDTO.getNumberOfPages()).thenReturn(100);
        when(request.getRequestDispatcher(PAGES_PRODUCTS_JSP)).thenReturn(requestDispatcher);

    }

    @Test
    public void testDoGetOnProperMethodInvocation() throws ServletException, IOException {
        productServlet.doGet(request, response);
        verify(request).setAttribute(PRICE_FROM, null);
        verify(request).setAttribute(PRICE_TO, null);
        verify(request).setAttribute(CATEGORIES_CHECK, null);
        verify(request).setAttribute(PRODUCER_COUNTRY, null);
        verify(request).setAttribute(PRODUCT_NAME, null);
        verify(request).setAttribute(SORT, null);
        verify(request).setAttribute(LIMIT_ARGUMENT, null);
    }

}