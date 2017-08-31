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
        closePrintWriter();
        closeGZipServletOutputStream();
    }

    @Override
    public void flushBuffer() {
        flushPrintWriter();
        tryFlushGZipServletOutputStream();
        trySuperFlushBuffer();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        tryGetOutputStream();
        return gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        tryGetPrintWriter();
        return printWriter;
    }

    @Override
    public void setContentLength(int len) {
        LOGGER.info("Method setContentLength() unimplemented");
    }

    private void closeGZipServletOutputStream() throws IOException {
        if (gzipOutputStream != null) {
            gzipOutputStream.close();
        }
    }

    private void closePrintWriter() {
        if (printWriter != null) {
            printWriter.close();
        }
    }

    private void flushPrintWriter() {
        if (printWriter != null) {
            printWriter.flush();
        }
    }

    private void tryFlushGZipServletOutputStream() {
        try {
            flushGZipServletOutputStream();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void flushGZipServletOutputStream() throws IOException {
        if (gzipOutputStream != null) {
            gzipOutputStream.flush();
        }
    }

    private void trySuperFlushBuffer() {
        try {
            super.flushBuffer();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void tryGetOutputStream() throws IOException {
        if (printWriter != null) {
            LOGGER.error("PrintWriter obtained already - cannot get OutputStream", new IllegalStateException());
        } else if (gzipOutputStream == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
        }
    }

    private void tryGetPrintWriter() throws IOException {
        if (printWriter == null && gzipOutputStream != null) {
            LOGGER.error("OutputStream obtained already - cannot get PrintWriter", new IllegalStateException());
        } else if (printWriter == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
            printWriter = new PrintWriter(new OutputStreamWriter(gzipOutputStream, getResponse().getCharacterEncoding()));
        }
    }
}