package com.epam.istore.strategy.impl;

import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionCaptchaStrategyTest {
    private static final String CAPTCHA_ID = "captchaId";
    private static final String CAPTCHA = "captcha";
    @Mock
    private CaptchaContainer captchaContainer;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Captcha captcha;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @InjectMocks
    private SessionCaptchaStrategy hiddenCaptchaStrategy;

    @Test
    public void testSetCaptchaMethodOnSettingCaptcha() {
        when(request.getSession()).thenReturn(session);
        hiddenCaptchaStrategy.setCaptcha(CAPTCHA_ID, captcha, request, response);
        verify(request).getSession();
        verify(session).setAttribute(CAPTCHA, captcha);
    }
}