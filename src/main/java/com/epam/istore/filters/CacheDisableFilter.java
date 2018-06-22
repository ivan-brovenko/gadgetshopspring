package com.epam.istore.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CacheDisableFilter implements Filter {
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String EXPIRES_HEADER_VALUE = "Expires";
    private static final String PRAGMA_HEADER_VALUE = "Pragma";
    private static final String NO_CACHE_NO_STORE_HEADER_VALUE = "no-cache, no-store";
    private static final String NO_CACHE_HEADER_VALUE = "no-cache";

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(CACHE_CONTROL, NO_CACHE_NO_STORE_HEADER_VALUE);
        response.setHeader(PRAGMA_HEADER_VALUE, NO_CACHE_HEADER_VALUE);
        response.setDateHeader(EXPIRES_HEADER_VALUE, 0);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
