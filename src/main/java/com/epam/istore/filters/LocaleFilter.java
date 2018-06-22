package com.epam.istore.filters;

import com.epam.istore.locale.strategy.LocaleStrategy;
import com.epam.istore.locale.strategy.LocaleStrategyContainer;
import com.epam.istore.locale.wrapper.LocaleWrapper;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;


public class LocaleFilter implements Filter {
    private static final String COMMA_CONSTANT = ",";
    private static final String EXPIRATION_TIME = "expirationTime";
    private static final String LOCALE_STRATEGY = "localeStrategy";
    private static final String DEFAULT_LOCALE = "defaultLocale";
    private static final String SUPPORTED_LOCALE_LIST = "supportedLocaleList";
    private static final String LANG = "lang";
    private static final String SERVLET_NAME = "servletName";
    private Locale defaultLocale;
    private List<Locale> listOfSupportedLocales;
    private LocaleStrategy localeStrategy;
    private LocaleStrategyContainer localeStrategyContainer;

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        Locale newLocale = getLocale(request);
        LocaleWrapper localeWrapper = new LocaleWrapper(request,newLocale);
        localeStrategy.setLocale(localeWrapper,response,newLocale);
        chain.doFilter(localeWrapper,response);
    }

    private Locale getLocale(HttpServletRequest request){
        Locale locale = getUserDefinedLocale(request);
        request.setAttribute(SERVLET_NAME,request.getRequestURI());
        if (locale != null){
            return locale;
        }
        locale = getStrategyDefinedLocale(request);
        if (locale!=null){
            return locale;
        }
        locale = getBrowserDefinedLocales(request);
        if (locale != null) {
            return locale;
        }
        return defaultLocale;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        int expirationTime = Integer.parseInt(config.getInitParameter(EXPIRATION_TIME));
        localeStrategyContainer = new LocaleStrategyContainer(expirationTime);
        String localeId = config.getInitParameter(LOCALE_STRATEGY);
        String defaultLocaleId = config.getInitParameter(DEFAULT_LOCALE);
        String supportedLocales = config.getInitParameter(SUPPORTED_LOCALE_LIST);
        localeStrategy = localeStrategyContainer.getStrategyById(localeId);
        defaultLocale = new Locale(defaultLocaleId);
        listOfSupportedLocales = parseLocale(supportedLocales);
    }

    private List<Locale> parseLocale(String supportedLocaleString){
        return Arrays.stream(supportedLocaleString.split(COMMA_CONSTANT))
                .map(LocaleUtils::toLocale)
                .collect(toList());
    }

    private Locale getStrategyDefinedLocale(HttpServletRequest request){
        return localeStrategy.getLocale(request);
    }

    private Locale getUserDefinedLocale(HttpServletRequest request){
        String localeName = request.getParameter(LANG);
        if (localeName !=null){
            for (Locale locale:listOfSupportedLocales){
                if (locale.equals(LocaleUtils.toLocale(localeName))){
                    return locale;
                }
            }
        }
        return null;
    }

    private Locale getBrowserDefinedLocales(HttpServletRequest request){
        Predicate<Locale> contains = locale -> listOfSupportedLocales.stream()
                .anyMatch(availableLocale->availableLocale.toString().contains(locale.toString())
                || locale.toString().contains(availableLocale.toString()));
        return Collections.list(request.getLocales()).stream()
                .filter(contains)
                .findFirst()
                .orElse(defaultLocale);
    }

}
