package com.epam.istore.locale.strategy;


import com.epam.istore.captcha.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public interface LocaleStrategy {
    void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale);

    Locale getLocale(HttpServletRequest request);
}
