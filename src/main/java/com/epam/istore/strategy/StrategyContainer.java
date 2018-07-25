package com.epam.istore.strategy;


import com.epam.istore.captcha.CaptchaContainer;
import com.epam.istore.strategy.impl.CookieCaptchaStrategy;
import com.epam.istore.strategy.impl.HiddenCaptchaStrategy;
import com.epam.istore.strategy.impl.SessionCaptchaStrategy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StrategyContainer {
    private final static String HIDDEN_FIELD_CAPTCHA_STRATEGY_KEY = "hidden";
    private final static String SESSION_CAPTCHA_STRATEGY_KEY = "session";
    private final static String COOKIE_CAPTCHA_STRATEGY_KEY = "cookie";
    private Map<String, CaptchaStrategy> strategies = new HashMap<String, CaptchaStrategy>();

    public StrategyContainer(CaptchaContainer captchaContainer, long timeout) {
        strategies.put(HIDDEN_FIELD_CAPTCHA_STRATEGY_KEY, new HiddenCaptchaStrategy(captchaContainer, timeout));
        strategies.put(SESSION_CAPTCHA_STRATEGY_KEY, new SessionCaptchaStrategy());
        strategies.put(COOKIE_CAPTCHA_STRATEGY_KEY, new CookieCaptchaStrategy(captchaContainer, timeout));
    }

    public CaptchaStrategy getStrategyById(String strategyId) {
        if (strategyId == null || !strategies.containsKey(strategyId)) {
            return null;
        }
        return strategies.get(strategyId);
    }
}
