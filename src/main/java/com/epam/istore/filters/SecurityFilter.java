package com.epam.istore.filters;

import com.epam.istore.bean.ConstraintBean;
import com.epam.istore.entity.User;
import com.epam.istore.security.AccessProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.epam.istore.messages.Messages.USER_ATTRIBUTE_NAME;


public class SecurityFilter implements Filter {
    private static final String REGISTRATION_PAGE = "/reg";
    private static final String ERROR_ACCESS_PAGE = "/error/403.jsp";
    private static final String PATH_TO_CONFIG_FILE = "/WEB-INF/security_config.xml";
    private ConstraintBean constraintBean;
    private AccessProvider accessProvider;
    private final static Logger LOGGER = Logger.getRootLogger();

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String currentURL = request.getRequestURI();
        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE_NAME);
        if (!accessProvider.isURLInConstraints(currentURL)) {
            chain.doFilter(req, resp);
            return;
        }
        if (user == null) {
            response.sendRedirect(REGISTRATION_PAGE);
            return;
        }
        if (!accessProvider.isRoleBindsWithCurrentURL(user.getRole(), currentURL)) {
            response.sendRedirect(ERROR_ACCESS_PAGE);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        try {
            File file = new File(config.getServletContext().getRealPath(PATH_TO_CONFIG_FILE));
            if (file.exists()) {
                constraintBean = parseXML(file);
                accessProvider = new AccessProvider(constraintBean.getConstraints());
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private ConstraintBean parseXML(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));
        return xmlMapper.readValue(xml, ConstraintBean.class);
    }

    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
