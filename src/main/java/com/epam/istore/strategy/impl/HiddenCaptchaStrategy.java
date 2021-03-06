package com.epam.istore.strategy.impl;


import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaContainer;
import com.epam.istore.strategy.CaptchaStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.epam.istore.messages.Messages.HIDDEN_ID;

@Component("hidden")
public class HiddenCaptchaStrategy implements CaptchaStrategy {

    @Autowired
    private CaptchaContainer captchaContainer;

    @Value("60")
    private long timeout;
    private ScheduledExecutorService executorService;

    public String getId(HttpServletRequest request) {
        return request.getParameter(HIDDEN_ID);
    }

    public void setCaptcha(String captchaId, Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(HIDDEN_ID,captchaId);
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
