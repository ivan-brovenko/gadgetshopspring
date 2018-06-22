package com.epam.istore.servlets;

import com.epam.istore.cart.Cart;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.Product;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.service.GadgetService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.istore.messages.Messages.APP_CONTEXT;


@WebServlet(name = "BasketServlet", urlPatterns = "/basket")
public class BasketServlet extends HttpServlet {
    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT_COUNT = "productCount";
    private static final String CART = "cart";
    private static final String APPLICATION_JSON = "application/json";
    private static final String UTF_8 = "UTF-8";
    private static final String CART_BEAN = "cartBean";
    private static final String BASKET_TOTAL_PRICE = "basketTotalPrice";
    private static final String PAGES_BASKET_JSP = "/pages/basket.jsp";
    private static final String TOTAL_PRICE = "totalPrice";
    private static final String TOTAL_COUNT = "totalCount";
    private ApplicationContext applicationContext;
    private GadgetService gadgetService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOGGER = Logger.getRootLogger();
    private Map<String, Object> cartMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.gadgetService = applicationContext.getGadgetService();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        int count = Integer.parseInt(request.getParameter(PRODUCT_COUNT));
        Cart cart = getCart(request);
        cart.set(id, count);
        sendResponse(response, request.getSession(), cart);
    }

    private Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    private void sendResponse(HttpServletResponse response, HttpSession session, Cart cart) throws IOException {
        putCountAndPriceFromCartToMap(cartMap, cart);
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        objectMapper.writeValue(response.getWriter(), cartMap);
        session.setAttribute(CART, cart);
        session.setAttribute(CART_BEAN, cartMap);
    }

    private void putCountAndPriceFromCartToMap(Map<String, Object> cartMap, Cart cart) {
        cartMap.put(TOTAL_PRICE, cart.getTotalPrice());
        cartMap.put(TOTAL_COUNT, cart.getTotalCount());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        request.setAttribute(BASKET_TOTAL_PRICE, cart.getTotalPrice());
        request.getRequestDispatcher(PAGES_BASKET_JSP).forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
            int count = Integer.parseInt(request.getParameter(PRODUCT_COUNT));
            Product product = gadgetService.getProductById(id);
            Cart cart = getCart(request);
            cart.addToCart(product, count);
            sendResponse(response, request.getSession(), cart);
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        if (StringUtils.isBlank(request.getParameter(PRODUCT_ID))) {
            cart.clear();
        } else {
            int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));
            cart.removeFromCart(productId);
        }
        sendResponse(response, request.getSession(), cart);
    }
}
