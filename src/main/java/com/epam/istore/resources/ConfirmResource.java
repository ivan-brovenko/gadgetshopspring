package com.epam.istore.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

public interface ConfirmResource {

    @PostMapping("/confirm")
    String submit(HttpServletRequest request);

    @GetMapping("/confirm")
    String createConfirmPage();
}
