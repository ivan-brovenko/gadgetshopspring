package com.epam.istore.resources;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


public interface ProductResource {

    @GetMapping("/products")
    String createProductPage(HttpServletRequest request);
}
