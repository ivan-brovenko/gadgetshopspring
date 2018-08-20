package com.epam.istore.resources.impl;

import com.epam.istore.resources.MainPageResource;
import org.springframework.stereotype.Controller;

@Controller
public class MainPageResourceImpl implements MainPageResource {

    @Override
    public String createMainPage() {
        return "index";
    }
}
