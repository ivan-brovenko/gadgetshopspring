package com.epam.istore.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrderResource {

    @PostMapping("/order")
    String submitOrder(HttpServletRequest request);

    @GetMapping("/order")
    String createOrderPage();
}
