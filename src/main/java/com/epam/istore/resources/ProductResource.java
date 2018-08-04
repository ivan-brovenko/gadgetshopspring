package com.epam.istore.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


public interface ProductResource {

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    String createProductPage(HttpServletRequest request);
}
