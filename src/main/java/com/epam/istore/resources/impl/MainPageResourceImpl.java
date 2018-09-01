package com.epam.istore.resources.impl;

import com.epam.istore.exception.GadgetShopException;
import com.epam.istore.resources.MainPageResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.PostConstruct;

@Controller
public class MainPageResourceImpl implements MainPageResource {

    @PostConstruct
    public void init(){
        System.out.println("POST CONSTRUCT");
    }

    @Override
    public String createMainPage() {
        return "index";
//        throw new GadgetShopException();

    }

    @ExceptionHandler(value = GadgetShopException.class)
    public String exception(){
        return "403";
    }
}
