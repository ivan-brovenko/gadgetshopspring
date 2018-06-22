package com.epam.istore.locale.strategy.impl;


import com.epam.istore.locale.strategy.LocaleStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class SessionLocaleStrategy implements LocaleStrategy {

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.getSession().setAttribute("locale",locale);
    }

    @Override
    public Locale getLocale(HttpServletRequest request) {
        return (Locale) request.getSession().getAttribute("locale");
    }
}
