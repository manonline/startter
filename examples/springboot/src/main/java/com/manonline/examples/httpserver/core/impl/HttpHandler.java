package com.manonline.examples.httpserver.core.impl;

/**
 * Created by davidqi on 1/23/17.
 */

import com.manonline.examples.httpserver.core.Handler;
import com.manonline.examples.httpserver.core.Request;
import com.manonline.examples.httpserver.core.Response;

public abstract class HttpHandler implements Handler {

    @Override
    public void service(Request request, Response response) {
        request.initRequestHeader();
        request.initRequestParam();
        if (request.getMethod().equals(Request.GET)) {
            doGet(request, response);
        } else if (request.getMethod().equals(Request.POST)) {
            request.initRequestBody();
            doPost(request, response);
        }
    }

    @Override
    public abstract void doGet(Request request, Response response);

    @Override
    public abstract void doPost(Request request, Response response);


}