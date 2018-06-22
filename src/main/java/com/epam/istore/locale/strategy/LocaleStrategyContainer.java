package com.epam.istore.locale.strategy;


import com.epam.istore.locale.strategy.LocaleStrategy;
import com.epam.istore.locale.strategy.impl.CookieLocaleStrategy;
import com.epam.istore.locale.strategy.impl.SessionLocaleStrategy;

import java.util.HashMap;
import java.util.Map;

public class LocaleStrategyContainer {
    private final static String SESSION_STRATEGY_KEY = "session";
    private final static String COOKIE_STRATEGY_KEY = "cookie";
    private Map<String, LocaleStrategy> strategies = new HashMap<String, LocaleStrategy>();

    public LocaleStrategyContainer(int expirationTime) {
        strategies.put(SESSION_STRATEGY_KEY, new SessionLocaleStrategy());
        strategies.put(COOKIE_STRATEGY_KEY, new CookieLocaleStrategy(expirationTime));
    }

    public LocaleStrategy getStrategyById(String strategyId) {
        if (strategyId == null || !strategies.containsKey(strategyId)) {
            return null;
        }
        return strategies.get(strategyId);
    }
}
