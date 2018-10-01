package com.epam.istore.resources;

import com.epam.istore.model.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


public interface ProductResource {

    @GetMapping("/products")
    String createProductPage(HttpServletRequest request, ProductDto productDto);
}
