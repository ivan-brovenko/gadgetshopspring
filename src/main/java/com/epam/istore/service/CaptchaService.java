package com.epam.istore.service;

import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaGenerator;
import com.epam.istore.strategy.CaptchaStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CaptchaService {
    private CaptchaStrategy captchaStrategy;
    private CaptchaGenerator captchaGenerator;

    public CaptchaService(CaptchaStrategy captchaContainer) {
        this.captchaStrategy = captchaContainer;
        this.captchaGenerator = new CaptchaGenerator();
    }

    public String getId(HttpServletRequest request) {
        return captchaStrategy.getId(request);
    }

    public void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        captchaStrategy.setCaptcha(captchaId, captcha, request, response);
    }

    public Captcha getCaptchaById(String captchaId, HttpServletRequest request) {
        return captchaStrategy.getCaptchaById(captchaId, request);
    }

    public Captcha generateCaptcha(long timeInterval) throws IOException {
        return captchaGenerator.generateCaptcha(timeInterval);
    }
}
