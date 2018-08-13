package com.epam.istore.resources.impl;

import com.epam.istore.entity.Order;
import com.epam.istore.exception.ServiceException;
import com.epam.istore.resources.ConfirmResource;
import com.epam.istore.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.istore.util.StringConstants.*;

@Controller
public class ConfirmResourceImpl implements ConfirmResource {

    private final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    private OrderService orderService;


    @PostMapping("/confirm")
    public String submit(HttpServletRequest request) {
        Order order = getOrder(request);
        try {
            HttpSession session = request.getSession();
            orderService.addOrder(order);
            session.removeAttribute(CART);
            session.removeAttribute(CART_BEAN);
            return "redirect:"+PAGES_SUCCESS_ORDER_JSP;
        } catch (ServiceException e) {
            LOGGER.error(e);
            return "redirect:"+CONFIRM;
        }
    }

    @GetMapping("/confirm")
    public String createConfirmPage() {
        return PAGES_CONFIRM_JSP;
    }

    private Order getOrder(HttpServletRequest request) {
        return (Order) request.getSession().getAttribute("order");
    }
}
