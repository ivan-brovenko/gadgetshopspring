package com.epam.istore.captcha;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class   CaptchaContainer {
    private Map<String, Captcha> captchaContainer = new HashMap<String, Captcha>();

    public void addCaptcha(String captchaId, Captcha captcha) {
        captchaContainer.put(captchaId, captcha);
    }

    public Captcha getCaptchaById(String captchaId) {
        return captchaContainer.get(captchaId);
    }

    public void removeCaptchaById(String captchaId) {
        captchaContainer.remove(captchaId);
    }

    @Override
    public String toString() {
        return "CaptchaContainer{" +
                "captchaContainer=" + captchaContainer +
                '}';
    }

    public Map<String, Captcha> getCaptchaContainer() {
        return captchaContainer;
    }
}
