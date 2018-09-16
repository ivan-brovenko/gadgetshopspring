package com.epam.istore.resources.impl;


import com.epam.istore.bean.RegFormBean;
import com.epam.istore.captcha.Captcha;
import com.epam.istore.converter.impl.RegFormConverter;
import com.epam.istore.exception.GadgetShopException;
import com.epam.istore.messages.Messages;
import com.epam.istore.resources.RegistrationResource;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.CaptchaService;
import com.epam.istore.service.UserService;
import com.epam.istore.util.RandomStringGenerator;
import com.epam.istore.validator.RegFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.CAPTCHA_ID;
import static com.epam.istore.messages.Messages.DUPLICATE_USER;
import static com.epam.istore.util.StringConstants.*;

@Controller
public class RegistrationResourceImpl implements RegistrationResource {
    private final static Logger logger = Logger.getRootLogger();

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RegFormValidator validator;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("60")
    private long timeout;

    @Autowired
    private RegFormBean regFormBean;

    @Autowired
    private UserService userService;

    private String addUserToUserService(UserService userService, RegFormBean regRegFormBean, HttpSession session, Map<String, String> errorMap) {
        try {
            userService.registerUser(new RegFormConverter().convert(regRegFormBean));
            return "redirect:" + MAIN_PAGE_LINK;
        } catch (Exception e) {
            logger.error("User with login " + regRegFormBean.getEmail() + " already registered", e);
            errorMap.put(DUPLICATE, DUPLICATE_USER);
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            return "redirect:" + REG_SERVLET_LINK;
        }
    }

    private void createCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Captcha captcha = captchaService.generateCaptcha(timeout);
        String captchaId = new RandomStringGenerator().getSaltString();
        captchaService.setCaptcha(captchaId, captcha, request, response);
        request.setAttribute(CAPTCHA_ID, captchaId);
    }

    private RegFormBean fill(HttpServletRequest request) {
        regFormBean.setName(request.getParameter(Messages.NAME));
        regFormBean.setSurname(request.getParameter(Messages.SURNAME));
        regFormBean.setEmail(request.getParameter(Messages.EMAIL));
        regFormBean.setPassword(request.getParameter(Messages.PASSWORD));
        regFormBean.setConfirmPassword(request.getParameter(Messages.CONFIRM_PASSWORD));
        regFormBean.setGender(request.getParameter(Messages.GENDER));
        regFormBean.setCaptchaNumbers(request.getParameter(Messages.CAPTCHA));
        return regFormBean;
    }

    @Override
    public String createRegistrationPage(HttpServletRequest request, HttpServletResponse response) {
        createCaptcha(request, response);
        return SIGN_UP_PAGE_LINK;
    }

    @Override
    public String submitRegistration(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        logger.debug(COMMAND_START);
        RegFormBean regRegFormBean = fill(request);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        HttpSession session = request.getSession();
        if (multipartFile.isEmpty()) {
            errorMap.put(PHOTO, EMPTY_PHOTO_ERROR);
        }
        if (errorMap.isEmpty()) {
//            avatarService.uploadAvatar(request, request.getParameter(EMAIL));
            try {
                File dir = new File(uploadPath);
                dir.mkdir();
                multipartFile.transferTo(new File(uploadPath+File.separator+request.getParameter(EMAIL)));
            } catch (IOException e) {
                logger.error(e);
                throw new GadgetShopException(e.getMessage(),e);
            }
            logger.debug("User " + regRegFormBean.getEmail() + " registered");
            return addUserToUserService(userService, regRegFormBean, session, errorMap);
        } else {
            logger.error("Errors occured, can't register user " + regRegFormBean.getEmail());
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            return "redirect:" + REG_SERVLET_LINK;
        }
    }
}
