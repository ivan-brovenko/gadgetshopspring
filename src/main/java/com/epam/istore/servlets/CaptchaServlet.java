package com.epam.istore.servlets;

import com.epam.istore.captcha.Captcha;
import com.epam.istore.service.CaptchaService;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.istore.messages.Messages.CAPTCHA_ID;
import static com.epam.istore.messages.Messages.CAPTCHA_SERVICE;


public class CaptchaServlet extends HttpServlet {
    private final static String IMAGE_CONTENT_TYPE = "image/png";
    private final static String PNG = "png";
    private CaptchaService captchaService;

    @Override
    public void init() throws ServletException {
        this.captchaService = (CaptchaService) getServletContext().getAttribute(CAPTCHA_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String captchaId = request.getParameter(CAPTCHA_ID);
        Captcha captcha = captchaService.getCaptchaById(captchaId, request);
        response.setContentType(IMAGE_CONTENT_TYPE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), PNG, outputStream);
        byte[] bytes = outputStream.toByteArray();
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        outputStream.close();
    }
}
