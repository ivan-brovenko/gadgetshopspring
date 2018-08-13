package com.epam.istore.servlets;

import com.epam.istore.entity.User;
import com.epam.istore.service.AvatarService;
import com.epam.istore.service.UserService;
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

import static com.epam.istore.messages.Messages.APP_CONTEXT;
import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogOutServletTest {
    private static final String REFERER = "referer";
    private final static String REDIRECT_TO_MAIN_PAGE = "/";
    private static final String TEST_EMAIL = "testEmail";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private AvatarService avatarService;
    @Mock
    private User user;
    @InjectMocks
    private LogOutServlet logOutServlet;

    @Before
    public void setUp() throws ServletException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(APP_CONTEXT)).thenReturn(applicationContext);
        when(applicationContext.getUserService()).thenReturn(userService);
        when(applicationContext.getAvatarService()).thenReturn(avatarService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER_ATTRIBUTE_NAME)).thenReturn(user);
        when(user.getEmail()).thenReturn(TEST_EMAIL);
        when(request.getHeader(REFERER)).thenReturn(REDIRECT_TO_MAIN_PAGE);
    }

    @Test
    public void testLogOutServletOnInvokeMethodInProperOrder() throws ServletException, IOException {
        logOutServlet.doPost(request, response);
        inOrder(request).verify(request).getSession();
        inOrder(userService).verify(userService).logout(session);
        inOrder(response).verify(response).sendRedirect(REDIRECT_TO_MAIN_PAGE);
    }
}