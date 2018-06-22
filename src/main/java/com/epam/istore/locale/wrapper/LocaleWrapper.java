package com.epam.istore.locale.wrapper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

public class LocaleWrapper extends HttpServletRequestWrapper {
    private Locale locale;

    public LocaleWrapper(HttpServletRequest request, Locale locale) {
        super(request);
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(Collections.singletonList(locale));
    }
}
