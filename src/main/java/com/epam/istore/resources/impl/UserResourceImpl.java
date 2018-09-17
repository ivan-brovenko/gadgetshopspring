package com.epam.istore.resources.impl;


import com.epam.istore.model.UserDto;
import com.epam.istore.captcha.Captcha;
import com.epam.istore.converter.impl.RegFormConverter;
import com.epam.istore.model.User;
import com.epam.istore.resources.UserResource;
import com.epam.istore.service.CaptchaService;
import com.epam.istore.service.UserService;
import com.epam.istore.util.FileUtil;
import com.epam.istore.util.RandomStringGenerator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;
import static com.epam.istore.util.StringConstants.*;

@Controller
@Log4j
public class UserResourceImpl implements UserResource {
    private static final String ERROR = "error";
    private static final String REFERER = "referer";

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private CaptchaService captchaService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("60")
    private long timeout;

    @Autowired
    private UserService userService;

    private void createCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Captcha captcha = captchaService.generateCaptcha(timeout);
        String captchaId = new RandomStringGenerator().getSaltString();
        captchaService.setCaptcha(captchaId, captcha, request, response);
        request.setAttribute(CAPTCHA_ID, captchaId);
    }

    @Override
    public String createRegistrationPage(HttpServletRequest request, HttpServletResponse response) {
        createCaptcha(request, response);
        return SIGN_UP_PAGE_LINK;
    }

    @Override
    public String submitRegistration(UserDto userDto) {
        log.debug("Start submit registration command");
        fileUtil.uploadFile(userDto.getFile(), userDto.getEmail());
        userService.registerUser(new RegFormConverter().convert(userDto));
        log.info("End submit registration command");
        return "redirect:" + MAIN_PAGE_LINK;
    }

    @Override
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        fileUtil.removeTempAvatar(request, user.getEmail());
        session.invalidate();
        return "redirect:" + request.getHeader(REFERER);
    }

    @Override
    public String logIn(UserDto userDto, HttpServletRequest request) {
        User user = userService.getAuthenticatedUser(userDto.getEmail(), userDto.getPassword());
        HttpSession session = request.getSession();
        if (user == null) {
            session.setAttribute(ERROR, AUTHENTICATE_ERROR);
        } else {
            fileUtil.uploadAvatarToTempFolder(request, user.getEmail());
            session.setAttribute(USER_ATTRIBUTE_NAME, user);
        }
        return "redirect:" + request.getHeader(REFERER);
    }
}

