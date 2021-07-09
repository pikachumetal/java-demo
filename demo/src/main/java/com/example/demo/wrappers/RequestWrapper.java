package com.example.demo.wrappers;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class RequestWrapper extends HttpServletRequestWrapper {
    private String requestData = null;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try (Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A")) {
            requestData = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        StringReader reader = new StringReader(requestData);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return reader.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                try {
                    if (isFinished()) listener.onAllDataRead();
                    else listener.onDataAvailable();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

            @Override
            public boolean isReady() {
                return isFinished();
            }

            @Override
            public boolean isFinished() {
                try {
                    return reader.read() < 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        };
    }

}