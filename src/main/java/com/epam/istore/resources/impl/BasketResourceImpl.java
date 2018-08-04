package com.epam.istore.resources.impl;


import com.epam.istore.cart.Cart;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.Product;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.resources.BasketResource;
import com.epam.istore.service.GadgetService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.istore.util.StringConstants.*;

@Controller
public class BasketResourceImpl implements BasketResource {

    @Autowired
    @Setter
    private GadgetService gadgetService;
    private Map<String, Object> cartMap = new HashMap<>();

    @Override
    public String postToBasket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        int count = Integer.parseInt(request.getParameter(PRODUCT_COUNT));
        Cart cart = getCart(request);
        cart.set(id, count);
        return sendResponse(request.getSession(), cart);
    }

    private Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    private void putCountAndPriceFromCartToMap(Map<String, Object> cartMap, Cart cart) {
        cartMap.put(TOTAL_PRICE, cart.getTotalPrice());
        cartMap.put(TOTAL_COUNT, cart.getTotalCount());
    }

    @Override
    public String createBasketPage(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = getCart(request);
        request.setAttribute(BASKET_TOTAL_PRICE, cart.getTotalPrice());
        return PAGES_BASKET_JSP;
    }

    @Override
    public String putToTheBasket(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        int count = Integer.parseInt(request.getParameter(PRODUCT_COUNT));
        Product product = gadgetService.getProductById(id);
        Cart cart = getCart(request);
        cart.addToCart(product, count);
        return sendResponse(request.getSession(), cart);
    }

    @Override
    public String deleteFromBasket(HttpServletRequest request) {
        Cart cart = getCart(request);
        if (StringUtils.isBlank(request.getParameter(PRODUCT_ID))) {
            cart.clear();
        } else {
            int productId = Integer.parseInt(request.getParameter(PRODUCT_ID));
            cart.removeFromCart(productId);
        }
        return sendResponse(request.getSession(), cart);
    }

    private String sendResponse(HttpSession session, Cart cart) {
        putCountAndPriceFromCartToMap(cartMap, cart);
        session.setAttribute(CART, cart);
        session.setAttribute(CART_BEAN, cartMap);
        return new Gson().toJson(cart);
    }
}
