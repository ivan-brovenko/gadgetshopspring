package com.epam.istore.resources;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface CaptchaResource {

    @RequestMapping(value = "/cap/{id}", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    byte[] createCaptcha(String captchaId, HttpServletRequest request) throws IOException;
}
