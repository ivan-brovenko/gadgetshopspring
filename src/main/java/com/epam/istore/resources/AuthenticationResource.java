package com.epam.istore.resources;

import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AuthenticationResource {

    @PostMapping("/login")
    String logIn(HttpServletRequest request) throws IOException;

    @PostMapping("/logout")
    String logOut(HttpServletRequest request);
}
