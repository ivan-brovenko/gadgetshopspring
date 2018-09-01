package com.epam.istore.resources.impl;

import com.epam.istore.bean.OrderBean;
import com.epam.istore.bean.ProductInCartBean;
import com.epam.istore.cart.Cart;
import com.epam.istore.converter.impl.OrderConverter;
import com.epam.istore.model.Order;
import com.epam.istore.model.OrderedProduct;
import com.epam.istore.model.User;
import com.epam.istore.resources.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;
import static com.epam.istore.util.StringConstants.*;

@Controller
public class OrderResourceImpl implements OrderResource {

    private Map<String,String> errorMap;
    private OrderBean orderBean;

    @Autowired
    private OrderConverter orderConverter;

    @PostConstruct
    public void init() {
        this.orderBean = new OrderBean();
        this.errorMap = new HashMap<>();
    }

    @Override
    public String submitOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (getUser(session) == null) {
            errorMap.put(ERROR_LOG_IN_MESSAGE,LOG_IN_ERROR);
            session.setAttribute(ERRORS,errorMap);
            return "redirect:"+request.getHeader(REFERER);
        }
        fillRequest(request);
        Order order = orderConverter.convert(orderBean);
        session.setAttribute(ORDER,order);
        return "redirect"+CONFIRM;
    }

    @Override
    public String createOrderPage() {
        return PAGES_ORDER_JSP;
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
