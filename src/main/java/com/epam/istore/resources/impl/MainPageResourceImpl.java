package com.epam.istore.resources.impl;

import com.epam.istore.exception.GadgetShopException;
import com.epam.istore.model.User;
import com.epam.istore.resources.MainPageResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Controller
public class MainPageResourceImpl implements MainPageResource {

    @PostConstruct
    public void init(){
        System.out.println("POST CONSTRUCT");
    }

    @Override
    public String createMainPage() {
        ModelAndView modelAndView = new ModelAndView();
        return "index";
//        throw new GadgetShopException();

    }

    @ExceptionHandler(value = GadgetShopException.class)
    public String exception(){
        return "403";
    }
}
