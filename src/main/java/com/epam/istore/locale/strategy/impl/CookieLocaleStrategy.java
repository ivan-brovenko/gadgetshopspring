package com.epam.istore.locale.strategy.impl;


import com.epam.istore.locale.strategy.LocaleStrategy;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.epam.istore.messages.Messages.CAPTCHA_ID;

public class CookieLocaleStrategy implements LocaleStrategy {
    private int maxAge;

    public CookieLocaleStrategy(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Cookie cookie = new Cookie("locale",locale.toString());
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    @Override
    public Locale getLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    return new Locale(cookie.getValue());
                }
            }
        }
        return null;
    }
}
