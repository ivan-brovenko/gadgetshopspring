package com.epam.istore.servlets;

import com.epam.istore.bean.RegFormBean;
import com.epam.istore.captcha.Captcha;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.converter.impl.RegFormConverter;
import com.epam.istore.exception.UserServiceException;
import com.epam.istore.messages.Messages;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.CaptchaService;
import com.epam.istore.service.UserService;
import com.epam.istore.util.RandomStringGenerator;
import com.epam.istore.validator.RegFormValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.epam.istore.messages.Messages.DUPLICATE_USER;
import static com.epam.istore.messages.Messages.EMPTY_PHOTO_ERROR;
import static com.epam.istore.messages.Messages.*;
import static com.epam.istore.messages.Messages.EMAIL;
import static com.epam.istore.messages.Messages.PHOTO;

@MultipartConfig(maxFileSize = 1024*1024*5)
public class RegistrationServlet extends HttpServlet {
    private static final String ERRORS = "errors";
    private final static Logger logger = Logger.getRootLogger();
    private ApplicationContext applicationContext;
    private CaptchaService captchaService;
    private RegFormValidator validator;
    private AvatarService avatarService;
    private final static String COMMAND_START = "Registration command start";
    private final static String BEAN = "bean";
    private final static String REG_SERVLET_LINK = "/reg";
    private final static String SIGN_UP_PAGE_LINK = "/pages/signup.jsp";
    private final static String MAIN_PAGE_LINK = "/";
    private RegFormBean regFormBean;

    @Override
    public void init() throws ServletException {
        this.applicationContext = (ApplicationContext) getServletContext().getAttribute(APP_CONTEXT);
        this.validator = applicationContext.getValidator();
        this.captchaService = (CaptchaService) getServletContext().getAttribute(CAPTCHA_SERVICE);
        this.avatarService = new AvatarService();
        this.regFormBean = new RegFormBean();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug(COMMAND_START);
        RegFormBean regRegFormBean = fill(request);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        UserService userService = applicationContext.getUserService();
        HttpSession session = request.getSession();
        if (request.getPart(PHOTO).getSize() == 0) {
            errorMap.put(PHOTO, EMPTY_PHOTO_ERROR);
        }
        if (errorMap.isEmpty()) {
            avatarService.uploadAvatar(request, request.getParameter(EMAIL));
            logger.debug("User " + regRegFormBean.getEmail() + " registered");
            addUserToUserService(userService, regRegFormBean, session, response, errorMap);
        } else {
            logger.error("Errors occured, can't register user " + regRegFormBean.getEmail());
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            response.sendRedirect(REG_SERVLET_LINK);
        }
    }

    private void addUserToUserService(UserService userService, RegFormBean regRegFormBean, HttpSession session, HttpServletResponse response, Map<String, String> errorMap) throws IOException {
        try {
            userService.add(new RegFormConverter().convert(regRegFormBean));
            response.sendRedirect(MAIN_PAGE_LINK);
        } catch (UserServiceException e) {
            logger.error("User with login " + regRegFormBean.getEmail() + " already registered", e);
            errorMap.put(DUPLICATE, DUPLICATE_USER);
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            response.sendRedirect(REG_SERVLET_LINK);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createCaptcha(request, response);
        request.getRequestDispatcher(SIGN_UP_PAGE_LINK).forward(request, response);
    }

    private void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long timeout = Long.parseLong(getServletContext().getInitParameter(TIMEOUT));
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

}
