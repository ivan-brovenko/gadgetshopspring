package com.epam.istore.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


public interface RegistrationResource {

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    String createRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    String submitRegistration(File file,HttpServletRequest request) throws IOException;
}
