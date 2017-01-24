package com.manonline.examples.httpserver;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.manonline.examples.httpserver.core.Handler;
import com.manonline.examples.httpserver.core.impl.HttpRequest;
import com.manonline.examples.httpserver.core.impl.HttpResponse;

/**
 * Created by davidqi on 1/23/17.
 */
public class MyHttpHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        HttpRequest request = new HttpRequest(httpExchange);
        HttpResponse response = new HttpResponse(httpExchange);
        Handler handler = Context.getHandler(request.getReuestURI().getPath());
        handler.service(request, response);
    }
}