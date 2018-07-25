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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
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

//@MultipartConfig(maxFileSize = 1024*1024*5)
@Controller
@RequestMapping("/reg")
public class RegistrationServlet {
    private static final String ERRORS = "errors";
    private final static Logger logger = Logger.getRootLogger();
    private CaptchaService captchaService;
    @Autowired
    private RegFormValidator validator;
    @Autowired
    private AvatarService avatarService;
    private final static String COMMAND_START = "Registration command start";
    private final static String BEAN = "bean";
    private final static String REG_SERVLET_LINK = "/reg";
    private final static String SIGN_UP_PAGE_LINK = "/pages/signup.jsp";
    private final static String MAIN_PAGE_LINK = "/";
    @Autowired
    private RegFormBean regFormBean;
    @Autowired
    @Qualifier("servletContext")
    private ServletContext servletContext;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        this.captchaService = (CaptchaService) servletContext.getAttribute(CAPTCHA_SERVICE);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postRequest(HttpServletRequest request) throws ServletException, IOException {
        logger.debug(COMMAND_START);
        RegFormBean regRegFormBean = fill(request);
        Map<String, String> errorMap = validator.validate(regRegFormBean, request);
        HttpSession session = request.getSession();
        if (request.getPart(PHOTO).getSize() == 0) {
            errorMap.put(PHOTO, EMPTY_PHOTO_ERROR);
        }
        if (errorMap.isEmpty()) {
            avatarService.uploadAvatar(request, request.getParameter(EMAIL));
            logger.debug("User " + regRegFormBean.getEmail() + " registered");
            return addUserToUserService(userService, regRegFormBean, session, errorMap);
        } else {
            logger.error("Errors occured, can't register user " + regRegFormBean.getEmail());
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            return "redirect:"+REG_SERVLET_LINK;
        }
    }

    private String addUserToUserService(UserService userService, RegFormBean regRegFormBean, HttpSession session, Map<String, String> errorMap) throws IOException {
        try {
            userService.add(new RegFormConverter().convert(regRegFormBean));
            return "redirect:"+MAIN_PAGE_LINK;
        } catch (UserServiceException e) {
            logger.error("User with login " + regRegFormBean.getEmail() + " already registered", e);
            errorMap.put(DUPLICATE, DUPLICATE_USER);
            session.setAttribute(BEAN, regRegFormBean);
            session.setAttribute(ERRORS, errorMap);
            return "redirect:"+REG_SERVLET_LINK;
        }
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        createCaptcha(request, response);
        return SIGN_UP_PAGE_LINK;
    }

    private void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long timeout = Long.parseLong(servletContext.getInitParameter(TIMEOUT));
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

    public CaptchaService getCaptchaService() {
        return captchaService;
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public RegFormValidator getValidator() {
        return validator;
    }

    public void setValidator(RegFormValidator validator) {
        this.validator = validator;
    }

    public AvatarService getAvatarService() {
        return avatarService;
    }

    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    public RegFormBean getRegFormBean() {
        return regFormBean;
    }

    public void setRegFormBean(RegFormBean regFormBean) {
        this.regFormBean = regFormBean;
    }
}
