package com.epam.istore.filters;

import com.epam.istore.locale.strategy.LocaleStrategy;
import com.epam.istore.locale.strategy.LocaleStrategyContainer;
import com.epam.istore.locale.strategy.impl.SessionLocaleStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {
    private static final String LANG = "lang";
    private static final String DEFAULT_LOCALE_VALUE = "en";
    private static final String EN = "en";
    private static final String RU = "ru";
    private static final String SUPPORTED_LOCALE_LIST = "supportedLocaleList";
    private static final String EXPIRATION_TIME = "expirationTime";
    private static final String DEFAULT_LOCALE = "defaultLocale";
    private static final String LOCALE_STRATEGY = "localeStrategy";
    private static final String EXPIRATION_TIME_VALUE = "500";
    private static final String COOKIE = "cookie";
    private static final String EN_RU = "en,ru";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private LocaleStrategy localeStrategy;
    @Mock
    private FilterChain filterChain;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private HttpSession session;
    @InjectMocks
    private LocaleFilter localeFilter;

    @Before
    public void setUp(){
        when(filterConfig.getInitParameter(SUPPORTED_LOCALE_LIST)).thenReturn(EN_RU);
        when(filterConfig.getInitParameter(EXPIRATION_TIME)).thenReturn(EXPIRATION_TIME_VALUE);
        when(filterConfig.getInitParameter(DEFAULT_LOCALE)).thenReturn(DEFAULT_LOCALE_VALUE);
        when(filterConfig.getInitParameter(LOCALE_STRATEGY)).thenReturn(COOKIE);

    }

    @Test
    public void testFilterOnProperMethodInvocationUsingUserLocale() throws ServletException, IOException {
        when(request.getParameter(LANG)).thenReturn(EN);
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request,response,filterChain);
        verify(request).getParameter(LANG);
        verify(localeStrategy,never()).getLocale(request);
        verify(request,never()).getLocales();
    }

    @Test
    public void testFilterOnProperMethodInvocationUsingDefaultLocale() throws ServletException, IOException {
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.singleton(new Locale(RU))));
        localeFilter.init(filterConfig);
        localeFilter.doFilter(request,response,filterChain);
        verify(request).getParameter(LANG);
        verify(request).getLocales();
    }
}