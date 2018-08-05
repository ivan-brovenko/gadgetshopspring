package com.epam.istore.servlets;

import com.epam.istore.captcha.Captcha;
import com.epam.istore.service.CaptchaService;


import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.epam.istore.messages.Messages.CAPTCHA_ID;
import static com.epam.istore.messages.Messages.CAPTCHA_SERVICE;

@WebServlet(name = "CaptchaServlet", urlPatterns = "/cap")
public class CaptchaServlet extends HttpServlet {

}
