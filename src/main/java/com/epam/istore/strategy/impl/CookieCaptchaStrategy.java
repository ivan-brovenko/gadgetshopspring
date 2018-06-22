package com.epam.istore.strategy.impl;


import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaContainer;
import com.epam.istore.strategy.CaptchaStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.epam.istore.messages.Messages.CAPTCHA_ID;

public class CookieCaptchaStrategy implements CaptchaStrategy {
    private final static int MAX_AGE = 60 * 60 * 24;
    private CaptchaContainer captchaContainer;
    private long timeout;
    private ScheduledExecutorService executorService;

    public CookieCaptchaStrategy(CaptchaContainer captchaContainer, long timeout) {
        this.captchaContainer = captchaContainer;
        this.timeout = timeout;
    }

    public String getId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CAPTCHA_ID)) {
                    return cookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(CAPTCHA_ID, captchaId);
        cookie.setMaxAge(MAX_AGE);
        response.addCookie(cookie);
        captchaContainer.removeCaptchaById(getId(request));
        captchaContainer.addCaptcha(captchaId, captcha);
    }

    public Captcha getCaptchaById(String captchaId, HttpServletRequest request) {
        if (executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::deleteCaptcha, timeout, timeout, TimeUnit.SECONDS);
        }
        return captchaContainer.getCaptchaById(captchaId);
    }

    private void deleteCaptcha() {
        captchaContainer.getCaptchaContainer().entrySet().stream()
                .filter((e) -> e.getValue()
                        .isExpired())
                .map(Map.Entry::getKey).forEach(captchaContainer.getCaptchaContainer()::remove);
    }

}
