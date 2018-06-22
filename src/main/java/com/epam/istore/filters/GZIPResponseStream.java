package com.epam.istore.filters;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZIPResponseStream extends ServletOutputStream {
    private GZIPOutputStream gzipStream;

    public GZIPResponseStream(HttpServletResponse response) throws IOException {
        super();
        gzipStream = new GZIPOutputStream(response.getOutputStream());
    }

    @Override
    public void close() throws IOException {
        gzipStream.flush();
        gzipStream.close();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() throws IOException {
        gzipStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        gzipStream.write(b);
    }

    @Override
    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }
}
