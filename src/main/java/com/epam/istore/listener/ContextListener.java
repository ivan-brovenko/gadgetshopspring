package com.epam.istore.listener;

import com.epam.istore.captcha.CaptchaContainer;
import com.epam.istore.context.ApplicationContext;
import com.epam.istore.service.CaptchaService;
import com.epam.istore.strategy.CaptchaStrategy;
import com.epam.istore.strategy.StrategyContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

import java.io.File;
import java.io.IOException;

import static com.epam.istore.messages.Messages.*;

@WebListener()
public class ContextListener implements ServletContextListener {
    private static final String CAPTCHA_STRATEGY = "CaptchaStrategy";

    public void contextInitialized(ServletContextEvent sce){
        ServletContext servletContext = sce.getServletContext();
        ApplicationContext applicationContext = new ApplicationContext();
        CaptchaContainer captchaContainer = new CaptchaContainer();
        servletContext.setAttribute(APP_CONTEXT, applicationContext);
        long timeout = Long.parseLong(servletContext.getInitParameter(TIMEOUT));
        StrategyContainer strategyContainer = new StrategyContainer(captchaContainer, timeout);
        CaptchaStrategy captchaStrategy = strategyContainer.getStrategyById(servletContext.getInitParameter(CAPTCHA_STRATEGY));
        CaptchaService captchaService = new CaptchaService(captchaStrategy);
        servletContext.setAttribute(CAPTCHA_SERVICE, captchaService);
        cleanTempFolder(servletContext);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        cleanTempFolder(sce.getServletContext());
    }

    private void cleanTempFolder(ServletContext servletContext){
        File tempDirectory = new File(servletContext.getRealPath("/temp"));
        File[] listOfFilesInTemp = tempDirectory.listFiles();
        if (listOfFilesInTemp != null) {
            for (File file : listOfFilesInTemp) {
                file.delete();
            }
        }
    }
}
