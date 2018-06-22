package com.epam.istore.servlets;

import com.epam.istore.cart.Cart;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.Order;
import com.epam.istore.exception.RepositoryException;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.istore.messages.Messages.APP_CONTEXT;


@WebServlet(name = "ConfirmServlet", urlPatterns = "/confirm")
public class ConfirmServlet extends HttpServlet {
    private static final String CART = "cart";
    private static final String CART_BEAN = "cartBean";
    private static final String PAGES_SUCCESS_ORDER_JSP = "/pages/success_order.jsp";
    private static final String CONFIRM = "/confirm";
    private static final String PAGES_CONFIRM_JSP = "/pages/confirm.jsp";
    private ApplicationContext applicationContext;
    private OrderService orderService;
    private final static Logger LOGGER = Logger.getRootLogger();

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.orderService = applicationContext.getOrderService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = getOrder(request);
        try {
            HttpSession session = request.getSession();
            orderService.addOrder(order);
            session.removeAttribute(CART);
            session.removeAttribute(CART_BEAN);
            response.sendRedirect(PAGES_SUCCESS_ORDER_JSP);
        } catch (ServiceException e) {
            LOGGER.error(e);
            response.sendRedirect(CONFIRM);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PAGES_CONFIRM_JSP).forward(request, response);
    }

    private Order getOrder(HttpServletRequest request) {
        return (Order) request.getSession().getAttribute("order");
    }

}
