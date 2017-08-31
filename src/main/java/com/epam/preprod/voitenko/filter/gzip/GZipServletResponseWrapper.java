package com.epam.preprod.voitenko.filter.gzip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {
    private static final Logger LOGGER = LogManager.getLogger(GZipServletResponseWrapper.class);

    private GZipServletOutputStream gzipOutputStream;
    private PrintWriter printWriter;

    public GZipServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (gzipOutputStream != null) {
            gzipOutputStream.close();
        }
    }

    @Override
    public void flushBuffer() {
        if (printWriter != null) {
            printWriter.flush();
        }

        try {
            if (gzipOutputStream != null) {
                gzipOutputStream.flush();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }

        try {
            super.flushBuffer();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (printWriter != null) {
            LOGGER.error("PrintWriter obtained already - cannot get OutputStream", new IllegalStateException());
        }
        if (gzipOutputStream == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
        }
        return gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null && gzipOutputStream != null) {
            LOGGER.error("OutputStream obtained already - cannot get PrintWriter", new IllegalStateException());
        }
        if (printWriter == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
            printWriter = new PrintWriter(new OutputStreamWriter(gzipOutputStream, getResponse().getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public void setContentLength(int len) {
        LOGGER.info("Method setContentLength() unimplemented");
    }
}