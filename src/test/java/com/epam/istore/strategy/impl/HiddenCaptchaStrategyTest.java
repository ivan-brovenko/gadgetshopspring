package com.epam.istore.strategy.impl;

import com.epam.istore.captcha.CaptchaContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HiddenCaptchaStrategyTest {
    private static final String HIDDEN_ID = "hiddenId";
    @Mock
    private CaptchaContainer captchaContainer;
    @Mock
    private HttpServletRequest request;
    private HiddenCaptchaStrategy hiddenCaptchaStrategy;

    @Before
    public void setUp() {
        hiddenCaptchaStrategy = new HiddenCaptchaStrategy(captchaContainer, 40);
    }

    @Test
    public void testGetIdMethodOnGettingHiddenIdFromRequest() {
        hiddenCaptchaStrategy.getId(request);
        verify(request).getParameter(HIDDEN_ID);
    }

}