package com.manonline.examples.httpserver.core.impl;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.manonline.examples.httpserver.core.Response;

/**
 * Created by davidqi on 1/23/17.
 */
public class HttpResponse implements Response {

    private HttpExchange httpExchange;

    public HttpResponse(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public void write(String result) {
        try {
            // 设置响应头属性及响应信息的长度
            httpExchange.sendResponseHeaders(200, result.length());
            // 获得输出流
            OutputStream out = httpExchange.getResponseBody();
            out.write(result.getBytes());
            out.flush();
            httpExchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}