package com.epam.istore.servlets;

import com.epam.istore.bean.OrderBean;
import com.epam.istore.cart.Cart;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.converter.impl.OrderConverter;
import com.epam.istore.bean.ProductInCartBean;
import com.epam.istore.entity.Order;
import com.epam.istore.entity.OrderedProduct;
import com.epam.istore.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.istore.messages.Messages.APP_CONTEXT;
import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;


@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    private static final String REFERER = "referer";
    private static final String LOG_IN_ERROR = "Log in before making an order!!!";
    private static final String ERROR_LOG_IN_MESSAGE = "errorLogInMessage";
    private static final String ORDER = "order";
    private static final String CONFIRM = "/confirm";
    private static final String PAGES_ORDER_JSP = "/pages/order.jsp";
    private static final String SHIP = "ship";
    private static final String BILL = "bill";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String CVV = "cvv";
    private static final String ADDRESS = "address";
    private static final String CART = "cart";
    private static final String ERRORS = "errors";
    private ApplicationContext applicationContext;
    private Map<String,String> errorMap;
    private OrderConverter orderConverter;
    private OrderBean orderBean;

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.orderConverter = applicationContext.getOrderConverter();
        this.orderBean = new OrderBean();
        this.errorMap = new HashMap<>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (getUser(session) == null) {
            errorMap.put(ERROR_LOG_IN_MESSAGE,LOG_IN_ERROR);
            session.setAttribute(ERRORS,errorMap);
            response.sendRedirect(request.getHeader(REFERER));
            return;
        }
        fillRequest(request);
        Order order = orderConverter.convert(orderBean);
        session.setAttribute(ORDER,order);
        response.sendRedirect(CONFIRM);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PAGES_ORDER_JSP).forward(request, response);
    }

    private OrderBean fillRequest(HttpServletRequest request){
        orderBean.setUserId(getUser(request.getSession()).getId());
        orderBean.setShipping(request.getParameter(SHIP));
        orderBean.setBilling(request.getParameter(BILL));
        orderBean.setCartNumber(request.getParameter(CARD_NUMBER));
        orderBean.setCvvNumber(request.getParameter(CVV));
        orderBean.setAddress(request.getParameter(ADDRESS));
        orderBean.setTotalPrice(getCart(request).getTotalPrice());
        Cart cart = getCart(request);
        List<ProductInCartBean> productList = cart.getAllProducts();
        List<OrderedProduct> orderedProductList = new ArrayList<>();
        productList.forEach((e)-> orderedProductList.add(new OrderedProduct(e.getProduct().getId(),e.getCountOfProduct(),e.getProduct().getPrice())));
        orderBean.setOrderedProducts(orderedProductList);
        return orderBean;
    }

    private Cart getCart(HttpServletRequest request){
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        if (cart == null){
            cart = new Cart();
        }
        return cart;
    }

    private User getUser(HttpSession session) {
        return (User) session.getAttribute(USER_ATTRIBUTE_NAME);
    }
}
