package com.epam.istore.resources;


import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface CaptchaResource {

    @GetMapping("/cap/{id}")
    byte[] createCaptcha(String captchaId, HttpServletRequest request) throws IOException;
}
