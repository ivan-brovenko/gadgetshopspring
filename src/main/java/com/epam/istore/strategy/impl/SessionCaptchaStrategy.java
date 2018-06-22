package com.epam.istore.strategy.impl;


import com.epam.istore.captcha.Captcha;
import com.epam.istore.strategy.CaptchaStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionCaptchaStrategy implements CaptchaStrategy {
    private final static String STUB_CAPTCHA_ID = "captcha";

    public String getId(HttpServletRequest request) {
        return STUB_CAPTCHA_ID;
    }

    public void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(STUB_CAPTCHA_ID, captcha);
    }

    public Captcha getCaptchaById(String captchaId, HttpServletRequest request) {
        return (Captcha) request.getSession().getAttribute(STUB_CAPTCHA_ID);
    }
}
