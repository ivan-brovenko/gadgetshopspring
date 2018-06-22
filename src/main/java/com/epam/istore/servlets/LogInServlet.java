package com.epam.istore.servlets;

import com.epam.istore.bean.LogInFormBean;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.PASSWORD;


@WebServlet(name = "LogInServlet", urlPatterns = "/login")
public class LogInServlet extends HttpServlet {
    private static final String ERROR = "error";
    public static final String REFERER = "referer";
    private ApplicationContext applicationContext;
    private UserService userService;
    private final static Logger LOGGER = Logger.getRootLogger();
    private AvatarService avatarService;
    private LogInFormBean logInFormBean;

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.userService = applicationContext.getUserService();
        this.avatarService = applicationContext.getAvatarService();
        this.logInFormBean = new LogInFormBean();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.sendRedirect(request.getHeader(REFERER));
    }

    private LogInFormBean fill(HttpServletRequest request){
        logInFormBean.setEmail(request.getParameter(EMAIL));
        logInFormBean.setPassword(request.getParameter(PASSWORD));
        return logInFormBean;
    }
}
