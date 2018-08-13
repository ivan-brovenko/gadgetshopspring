package com.epam.istore.servlets;

import com.epam.istore.entity.Order;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.service.OrderService;
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

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmServletTest {
    private static final String ORDER = "order";
    private static final String CART = "cart";
    private static final String CART_BEAN = "cartBean";
    private static final String PAGES_SUCCESS_ORDER_JSP = "/pages/success_order.jsp";
    private static final String PAGES_CONFIRM_JSP = "/pages/confirm.jsp";
    @Mock
    private OrderService orderService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Order order;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @InjectMocks
    private ConfirmServlet confirmServlet;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ORDER)).thenReturn(order);
        when(request.getRequestDispatcher(PAGES_CONFIRM_JSP)).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPostOnAddingOrderToOrderServiceInProperOrder() throws ServiceException, IOException, ServletException {
        confirmServlet.doPost(request, response);
        inOrder(session).verify(session).getAttribute(ORDER);
        inOrder(orderService).verify(orderService).addOrder(order);
        inOrder(session).verify(session).removeAttribute(CART);
        inOrder(session).verify(session).removeAttribute(CART_BEAN);
        inOrder(response).verify(response).sendRedirect(PAGES_SUCCESS_ORDER_JSP);
    }

    @Test
    public void testDoGetOnForwardingToTheRightPage() throws ServletException, IOException {
        confirmServlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}