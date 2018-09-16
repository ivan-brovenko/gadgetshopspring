package com.epam.istore.resources;


import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface BasketResource {

    @GetMapping("/basket")
    String createBasketPage(HttpServletRequest request) throws IOException;

    @PutMapping("/basket")
    @ResponseBody
    String putToTheBasket(int productId, int productCount, HttpServletRequest request);

    @DeleteMapping("/basket")
    @ResponseBody
    String deleteFromBasket(HttpServletRequest request) throws ServletException, IOException;

    @PostMapping("/basket")
    @ResponseBody
    String postToBasket(HttpServletRequest request) throws IOException;
}
