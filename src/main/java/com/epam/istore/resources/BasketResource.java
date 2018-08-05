package com.epam.istore.resources;


import com.epam.istore.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BasketResource {

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    String createBasketPage(HttpServletRequest request) throws IOException;

    @RequestMapping(value = "/basket", method = RequestMethod.PUT)
    @ResponseBody
    String putToTheBasket(int productId, int productCount, HttpServletRequest request) throws ServiceException;

    @RequestMapping(value = "/basket", method = RequestMethod.DELETE)
    @ResponseBody
    String deleteFromBasket(HttpServletRequest request) throws ServletException, IOException;

    @RequestMapping(value = "/basket", method = RequestMethod.POST)
    @ResponseBody
    String postToBasket(HttpServletRequest request) throws IOException;
}
