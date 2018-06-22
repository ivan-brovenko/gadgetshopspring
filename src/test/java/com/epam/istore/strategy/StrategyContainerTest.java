package com.epam.istore.strategy;

import com.epam.istore.captcha.CaptchaContainer;
import com.epam.istore.strategy.impl.CookieCaptchaStrategy;
import com.epam.istore.strategy.impl.HiddenCaptchaStrategy;
import com.epam.istore.strategy.impl.SessionCaptchaStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyContainerTest {
    private static final String SESSION = "session";
    private static final String COOKIE = "cookie";
    private static final String HIDDEN = "hidden";
    @Mock
    private CaptchaContainer captchaContainer;
    private StrategyContainer strategyContainer;

    @Before
    public void setUp() {
        strategyContainer = new StrategyContainer(captchaContainer, 100);
    }

    @Test
    public void testGettingSessionStrategyFromContainer() {
        assertTrue(strategyContainer.getStrategyById(SESSION) instanceof SessionCaptchaStrategy);
    }

    @Test
    public void testGettingCookieStrategyFromContainer() {
        assertTrue(strategyContainer.getStrategyById(COOKIE) instanceof CookieCaptchaStrategy);
    }

    @Test
    public void testGettingHiddenFieldStrategyFromContainer() {
        assertTrue(strategyContainer.getStrategyById(HIDDEN) instanceof HiddenCaptchaStrategy);
    }

}