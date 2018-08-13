package com.epam.istore.resources.impl;

import com.epam.istore.bean.LogInFormBean;
import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.resources.AuthenticationResource;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.istore.messages.Messages.*;

@Controller
public class AuthenticationResourceImpl implements AuthenticationResource {
    private static final String ERROR = "error";
    private static final String REFERER = "referer";
    private final static Logger LOGGER = Logger.getRootLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private LogInFormBean logInFormBean;

    private LogInFormBean fill(HttpServletRequest request) {
        logInFormBean.setEmail(request.getParameter(EMAIL));
        logInFormBean.setPassword(request.getParameter(PASSWORD));
        return logInFormBean;
    }

    @Override
    public String logIn(HttpServletRequest request) throws IOException {
        LogInFormBean logInFormBean = fill(request);
        try {
            User user = userService.getAuthenticatedUser(logInFormBean.getEmail(), logInFormBean.getPassword());
            HttpSession session = request.getSession();
            if (user == null) {
                session.setAttribute(ERROR, AUTHENTICATE_ERROR);
            } else {
                avatarService.uploadAvatarToTempFolder(request, user.getEmail());
                session.setAttribute(USER_ATTRIBUTE_NAME, user);
            }
        } catch (AuthenticationException e) {
            LOGGER.error("Can't authenticate user " + logInFormBean.getEmail(), e);
        }
        return "redirect:" + request.getHeader(REFERER);
    }

    @Override
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        avatarService.removeTempAvatar(request, user.getEmail());
        userService.logout(session);
        return "redirect:" + request.getHeader(REFERER);
    }
}
