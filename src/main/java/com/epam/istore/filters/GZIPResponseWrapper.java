package com.epam.istore.filters;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {
    private static final String WRITER_ILLEGAL_STATE_EXCEPTION = "getWriter() has already been called!";
    private static final String OUTPUT_STREAM_ILLEGAL_STATE_EXCEPTION = "getOutputStream() has already been called!";
    private static final String UTF_8_CONSTANT = "UTF-8";
    private HttpServletResponse response;
    private ServletOutputStream stream;
    private final static Logger LOGGER = Logger.getRootLogger();
    private PrintWriter writer;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    private ServletOutputStream createOutputStream() throws IOException {
        return new GZIPResponseStream(response);
    }

    public void closeResponse() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException(WRITER_ILLEGAL_STATE_EXCEPTION);
        }
        if (stream == null)
            stream = createOutputStream();
        return stream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (Objects.nonNull(writer)) {
            return writer;
        }

        if (Objects.nonNull(stream)) {
            throw new IllegalStateException(OUTPUT_STREAM_ILLEGAL_STATE_EXCEPTION);
        }

        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, UTF_8_CONSTANT));
        return (writer);
    }
}
