package com.epam.istore.servlets;

import com.epam.istore.bean.RegFormBean;
import com.epam.istore.captcha.Captcha;
import com.epam.istore.captcha.CaptchaGenerator;
import com.epam.istore.entity.User;
import com.epam.istore.exception.UserServiceException;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.CaptchaService;
import com.epam.istore.service.UserService;
import com.epam.istore.validator.RegFormValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {
    private final static String REDIRECT_WITH_CORRECT_INFO = "/";
    private final static String REDIRECT_WITH_INCORRECT_INFO = "/reg";
    private final static String APP_CONTEXT = "appContext";
    private final static String TIMEOUT = "timeout";
    private final static String TIMEOUT_VALUE = "10";
    private final static String MALE = "male";
    private final static String FAKE_ERROR_KEY = "error key";
    private final static String FAKE_ERROR_VALUE = "error value";
    private final static int ONE_TIME = 1;
    private final static String CAPTCHA_ID_TEST_VALUE = "666";
    private final static String CAPTCHA_SERVICE = "captchaService";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Captcha captcha;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private CaptchaService captchaService;
    @Mock
    private CaptchaGenerator captchaGenerator;
    @Mock
    private UserService userService;
    @Mock
    private RegFormValidator validator;
    @Mock
    private RegFormBean regFormBean;
    @Mock
    private Part part;
    @Mock
    private AvatarService avatarService;
    @InjectMocks
    private RegistrationServlet registrationServlet;

    @Before
    public void setUp() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(APP_CONTEXT)).thenReturn(applicationContext);
        when(servletContext.getAttribute(CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getId(request)).thenReturn(CAPTCHA_ID_TEST_VALUE);
        when(captchaService.generateCaptcha(anyLong())).thenReturn(captcha);
        when(captchaService.getCaptchaById(CAPTCHA_ID_TEST_VALUE, request)).thenReturn(captcha);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(captcha.getCaptchaNumber()).thenReturn(CAPTCHA_ID_TEST_VALUE);
        when(request.getServletContext().getInitParameter(TIMEOUT)).thenReturn(TIMEOUT_VALUE);
        when(request.getSession()).thenReturn(session);
        when(part.getSize()).thenReturn(new Long(10));
        when(request.getPart(anyString())).thenReturn(part);
        setUpApplicationContext();
        setUpCaptchaGenerator();
        setUpFormBean();

    }

    private void setUpApplicationContext() {
        when(applicationContext.getCaptchaGenerator()).thenReturn(captchaGenerator);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(applicationContext.getValidator()).thenReturn(validator);
        when(applicationContext.getAvatarService()).thenReturn(avatarService);
    }

    private void setUpCaptchaGenerator() throws IOException {
        when(captchaGenerator.generateCaptcha(anyLong())).thenReturn(captcha);
    }

    private void setUpFormBean() {
        when(regFormBean.getGender()).thenReturn(MALE);
    }


    @Test
    public void testDoPostOnInvokingMethodsInCertainOrder() throws ServletException, IOException, UserServiceException {
        registrationServlet.doPost(request, response);
        inOrder(validator).verify(validator).validate(regFormBean, request);
        inOrder(userService).verify(userService).add(any(User.class));
        inOrder(response).verify(response).sendRedirect(REDIRECT_WITH_CORRECT_INFO);
    }

    @Test
    public void testRequestDispatcherCallInDoGetMethod() throws ServletException, IOException {
        registrationServlet.doGet(request, response);
        verify(requestDispatcher, times(ONE_TIME)).forward(request, response);
    }

    @Test
    public void testSendRedirectInDoPostMethod() throws ServletException, IOException {
        registrationServlet.doPost(request, response);
        verify(response, times(ONE_TIME)).sendRedirect(REDIRECT_WITH_CORRECT_INFO);
    }

    @Test
    public void testSendRedirectInDoPostMethodWhileError() throws ServletException, IOException {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(FAKE_ERROR_KEY, FAKE_ERROR_VALUE);
        when(validator.validate(regFormBean, request)).thenReturn(errorMap);
        registrationServlet.doPost(request, response);
        verify(response).sendRedirect(REDIRECT_WITH_INCORRECT_INFO);
    }
}