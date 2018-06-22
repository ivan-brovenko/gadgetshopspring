package com.epam.istore.strategy;


import com.epam.istore.captcha.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaStrategy {

    String getId(HttpServletRequest request);

    void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response);

    Captcha getCaptchaById(String captchaId, HttpServletRequest request);
}
