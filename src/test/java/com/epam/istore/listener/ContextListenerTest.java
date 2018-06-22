package com.epam.istore.listener;

import com.epam.istore.context.ApplicationContext;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static com.epam.istore.messages.Messages.APP_CONTEXT;
import static com.epam.istore.messages.Messages.TIMEOUT;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContextListenerTest {
    private static final String TIMEOUT_VALUE = "60";
    private static final String CAPTCHA_STRATEGY = "CaptchaStrategy";
    private static final String TEMP = "/temp";
    @InjectMocks
    private ContextListener contextListener;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletContextEvent servletContextEvent;

    @Before
    public void setUp() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(TIMEOUT)).thenReturn(TIMEOUT_VALUE);
        when(servletContext.getRealPath(TEMP)).thenReturn(StringUtils.EMPTY);
    }

    @Test
    public void testProperMethodInvocationAfterContextInitialized() {
        contextListener.contextInitialized(servletContextEvent);
        inOrder(servletContextEvent).verify(servletContextEvent).getServletContext();
        inOrder(servletContext).verify(servletContext).getInitParameter(TIMEOUT);
        inOrder(servletContext).verify(servletContext).getInitParameter(CAPTCHA_STRATEGY);
        inOrder(servletContext).verify(servletContext).getRealPath(TEMP);
    }

}