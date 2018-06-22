package com.epam.istore.servlets;

import com.epam.istore.bean.LogInFormBean;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.entity.User;
import com.epam.istore.exception.AuthenticationException;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.epam.istore.messages.Messages.AUTHENTICATE_ERROR;
import static com.epam.istore.messages.Messages.APP_CONTEXT;
import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogInServletTest {
    private static final String CORRECT_EMAIL = "correct email";
    private static final String CORRECT_PASSWORD = "correct password";
    private static final String INCORRECT_EMAIL = "incorrect email";
    private static final String INCORRECT_PASSWORD = "incorrect password";
    private static final String ERROR = "error";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private HttpSession session;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private User user;
    @Mock
    private LogInFormBean logInFormBean;
    @Mock
    private AvatarService avatarService;
    @InjectMocks
    private LogInServlet logInServlet;

    @Before
    public void setUp() throws ServletException, AuthenticationException {
        when(applicationContext.getAvatarService()).thenReturn(avatarService);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(servletContext.getAttribute(APP_CONTEXT)).thenReturn(applicationContext);
        when(request.getSession()).thenReturn(session);
        when(userService.getAuthenticatedUser(CORRECT_EMAIL, CORRECT_PASSWORD)).thenReturn(user);
    }

    @Test
    public void testOnLoginWithCorrectLoginAndPassword() throws ServletException, IOException {
        when(logInFormBean.getEmail()).thenReturn(CORRECT_EMAIL);
        when(logInFormBean.getPassword()).thenReturn(CORRECT_PASSWORD);
        logInServlet.doPost(request, response);
        verify(session).setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    @Test
    public void testOnErrorWithIncorrectPassword() throws ServletException, IOException {
        when(logInFormBean.getEmail()).thenReturn(INCORRECT_EMAIL);
        when(logInFormBean.getPassword()).thenReturn(INCORRECT_PASSWORD);
        logInServlet.doPost(request, response);
        verify(session).setAttribute(ERROR, AUTHENTICATE_ERROR);
    }

}