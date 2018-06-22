package com.epam.istore.servlets;

import com.epam.istore.bean.OrderBean;
import com.epam.istore.cart.Cart;
import com.epam.istore.converter.impl.OrderConverter;
import com.epam.istore.entity.Order;
import com.epam.istore.entity.User;
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
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServletTest {
    private static final String SHIP = "ship";
    private static final String SHIP_PARAM = "ship_param";
    private static final String BILL = "bill";
    private static final String BILL_PARAM = "bill_param";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String CART_NUMBER = "666";
    private static final String CONFIRM = "/confirm";
    private static final String USER = "_user";
    private static final String CART = "cart";
    private static final String ERROR_LOG_IN_MESSAGE = "errorLogInMessage";
    private static final String LOG_IN_BEFORE_MAKING_AN_ORDER = "Log in before making an order!!!";
    private static final String REFERER = "referer";
    private static final String REFERER_SLASH = "/referer";
    private static final String PAGES_ORDER_JSP = "/pages/order.jsp";
    private static final int USER_ID_VALUE = 1;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private OrderBean orderBean;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderConverter orderConverter;
    @Mock
    private Cart cart;
    @Mock
    private User user;
    @InjectMocks
    private OrderServlet orderServlet;
    @Mock
    private Map<String,String> errorMap;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(user);
        when(session.getAttribute(CART)).thenReturn(cart);
        when(user.getId()).thenReturn(USER_ID_VALUE);
        when(request.getParameter(SHIP)).thenReturn(SHIP_PARAM);
        when(request.getParameter(BILL)).thenReturn(BILL_PARAM);
        when(request.getParameter(CARD_NUMBER)).thenReturn(CART_NUMBER);
        when(request.getHeader(REFERER)).thenReturn(REFERER_SLASH);
        when(request.getRequestDispatcher(PAGES_ORDER_JSP)).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPostOnProperInvocationMethods() throws ServletException, IOException {
        orderServlet.doPost(request, response);
        verify(request, times(4)).getSession();
        verify(session, times(2)).getAttribute(USER);
        verify(user).getId();
        verify(orderBean).setUserId(USER_ID_VALUE);
        verify(request).getParameter(SHIP);
        verify(orderBean).setShipping(SHIP_PARAM);
        verify(request).getParameter(BILL);
        verify(orderBean).setBilling(BILL_PARAM);
        verify(request).getParameter(CARD_NUMBER);
        verify(orderBean).setCartNumber(CART_NUMBER);
        verify(orderConverter).convert(orderBean);
        verify(response).sendRedirect(CONFIRM);
    }

    @Test
    public void testDoPostOnProperOrderMethodInvocationIfUserIsNull() throws ServletException, IOException {
        when(session.getAttribute(USER)).thenReturn(null);
        orderServlet.doPost(request, response);
        verify(request).getSession();
        verify(session).getAttribute(USER);
        verify(errorMap).put(ERROR_LOG_IN_MESSAGE, LOG_IN_BEFORE_MAKING_AN_ORDER);
        verify(request).getHeader(REFERER);
        verify(response).sendRedirect(REFERER_SLASH);
    }

    @Test
    public void testDoGetOnForwardToProperPage() throws ServletException, IOException {
        orderServlet.doGet(request, response);
        inOrder(request).verify(request).getRequestDispatcher(PAGES_ORDER_JSP);
        inOrder(requestDispatcher).verify(requestDispatcher).forward(request, response);
    }
}