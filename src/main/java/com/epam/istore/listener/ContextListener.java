package com.epam.istore.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.File;

@WebListener()
public class ContextListener implements ServletContextListener {
    private static final String CAPTCHA_STRATEGY = "CaptchaStrategy";

    public void contextInitialized(ServletContextEvent sce){
        ServletContext servletContext = sce.getServletContext();
//        CaptchaContainer captchaContainer = new CaptchaContainer();
//        long timeout = Long.parseLong(servletContext.getInitParameter(TIMEOUT));
//        StrategyContainer strategyContainer = new StrategyContainer(captchaContainer, timeout);
//        CaptchaStrategy captchaStrategy = strategyContainer.getStrategyById(servletContext.getInitParameter(CAPTCHA_STRATEGY));
//        CaptchaService captchaService = new CaptchaService(captchaStrategy);
//        servletContext.setAttribute(CAPTCHA_SERVICE, captchaService);
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
