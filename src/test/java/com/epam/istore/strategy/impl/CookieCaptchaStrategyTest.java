package com.epam.istore.strategy.impl;

import com.epam.istore.captcha.CaptchaContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CookieCaptchaStrategyTest {
    @Mock
    private CaptchaContainer captchaContainer;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Cookie cookie;
    private Cookie[] cookies = new Cookie[2];
    CookieCaptchaStrategy cookieCaptchaStrategy;

    @Before
    public void setUp() {
        when(request.getCookies()).thenReturn(cookies);
        for (int i = 0; i < cookies.length; i++) {
            cookies[i] = cookie;
        }
        when(cookie.getName()).thenReturn("id");
        cookieCaptchaStrategy = new CookieCaptchaStrategy(captchaContainer, 100);
    }

    @Test
    public void testGetIdMethodOnGettingCookiesFromRequest() {
        cookieCaptchaStrategy.getId(request);
        inOrder(request).verify(request).getCookies();
        inOrder(cookie).verify(cookie).getValue();
    }

}