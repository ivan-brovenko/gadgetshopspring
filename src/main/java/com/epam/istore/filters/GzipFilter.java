package com.epam.istore.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;


public class GzipFilter implements Filter {
    private static final Logger LOGGER = Logger.getRootLogger();
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String ACCEPT_ENCODING = "accept-encoding";
    private static final String GZIP = "gzip";
    private static final String HEADER_ACCEPT_VALUE = "Accept";
    private static final String HEADER_TEXT_VALUE = "text/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (isClientExpectText(req) && isClientReadyUnzip(req)) {
            GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(resp);
            resp.addHeader(CONTENT_ENCODING, GZIP);
            chain.doFilter(req, wrappedResponse);
            wrappedResponse.closeResponse();
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isClientExpectText( HttpServletRequest req) {
        return Objects.nonNull(req.getHeader(HEADER_ACCEPT_VALUE)) && req.getHeader(HEADER_ACCEPT_VALUE).contains(HEADER_TEXT_VALUE);
    }

    private boolean isClientReadyUnzip(HttpServletRequest req) {
        return Objects.nonNull(req.getHeader(ACCEPT_ENCODING)) && req.getHeader(ACCEPT_ENCODING).contains(GZIP);
    }

    @Override
    public void destroy() {
    }
}
