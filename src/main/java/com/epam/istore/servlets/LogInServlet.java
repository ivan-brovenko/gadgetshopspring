package com.epam.istore.servlets;

import com.epam.istore.bean.LogInFormBean;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.PASSWORD;


//@WebServlet(name = "LogInServlet", urlPatterns = "/login")
@Controller
public class LogInServlet {
    private static final String ERROR = "error";
    private static final String REFERER = "referer";
    @Autowired
    private UserService userService;
    private final static Logger LOGGER = Logger.getRootLogger();
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private LogInFormBean logInFormBean;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) throws IOException {
        LogInFormBean logInFormBean = fill(request);
        try {
            User user = userService.getAuthenticatedUser(logInFormBean.getEmail(), logInFormBean.getPassword());
            HttpSession session = request.getSession();
            if (user == null) {
                session.setAttribute(ERROR, AUTHENTICATE_ERROR);
            } else {
                avatarService.uploadAvatarToTempFolder(request,user.getEmail());
                session.setAttribute(USER_ATTRIBUTE_NAME, user);
            }
        } catch (AuthenticationException e) {
            LOGGER.error("Can't authenticate user "+logInFormBean.getEmail(),e);
        }
        System.out.println(request.getHeader(REFERER)+"refeeeeeeeeerer");
        return "redirect:/";
    }

    private LogInFormBean fill(HttpServletRequest request){
        logInFormBean.setEmail(request.getParameter(EMAIL));
        logInFormBean.setPassword(request.getParameter(PASSWORD));
        return logInFormBean;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AvatarService getAvatarService() {
        return avatarService;
    }

    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    public LogInFormBean getLogInFormBean() {
        return logInFormBean;
    }

    public void setLogInFormBean(LogInFormBean logInFormBean) {
        this.logInFormBean = logInFormBean;
    }
}
