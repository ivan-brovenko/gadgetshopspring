package com.epam.istore.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 18.08.2018.
 */
public interface MainPageResource {

    @GetMapping("/")
    String createMainPage();
}
