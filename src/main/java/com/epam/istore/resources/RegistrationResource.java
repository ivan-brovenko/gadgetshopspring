package com.epam.istore.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


public interface RegistrationResource {

    @GetMapping("/reg")
    String createRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @PostMapping("/reg")
    String submitRegistration(File file,HttpServletRequest request) throws IOException;
}
