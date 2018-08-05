package com.epam.istore.resources.impl;


import com.epam.istore.captcha.Captcha;
import com.epam.istore.resources.CaptchaResource;
import com.epam.istore.service.CaptchaService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.istore.messages.Messages.CAPTCHA_ID;
import static com.epam.istore.messages.Messages.CAPTCHA_SERVICE;

@Controller
public class CaptchaResourceImpl implements CaptchaResource {
    private final static String IMAGE_CONTENT_TYPE = "image/png";
    private final static String PNG = "png";

    @Setter
    @Autowired
    private CaptchaService captchaService;

    @Override
    public byte[] createCaptcha(@PathVariable(CAPTCHA_ID) String captchaId, HttpServletRequest request) throws IOException {
        Captcha captcha = captchaService.getCaptchaById(captchaId, request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), PNG, outputStream);
        return outputStream.toByteArray();
    }
}
