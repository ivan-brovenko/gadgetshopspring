package com.epam.istore.resources;


import com.epam.istore.model.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserResource {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String logIn(UserDto userDto, HttpServletRequest request);

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    String logOut(HttpServletRequest request);

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    String createRegistrationPage(HttpServletRequest request, HttpServletResponse response);

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    String submitRegistration(UserDto userDto);
}
