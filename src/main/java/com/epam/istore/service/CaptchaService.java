package com.epam.istore.service;

import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaGenerator;
import com.epam.istore.strategy.CaptchaStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaService {

    @Autowired
    @Qualifier("session")
    private CaptchaStrategy captchaStrategy;

    @Autowired
    private CaptchaGenerator captchaGenerator;

    public String getId(HttpServletRequest request) {
        return captchaStrategy.getId(request);
    }

    public void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        captchaStrategy.setCaptcha(captchaId, captcha, request, response);
    }

    public Captcha getCaptchaById(String captchaId, HttpServletRequest request) {
        return captchaStrategy.getCaptchaById(captchaId, request);
    }

    public Captcha generateCaptcha(long timeInterval){
        return captchaGenerator.generateCaptcha(timeInterval);
    }
}
