package com.manonline.examples.httpserver.core;

/**
 * Created by davidqi on 1/23/17.
 */
public interface Handler {
    public void service(Request request, Response response);

    public void doGet(Request request, Response response);

    public void doPost(Request request, Response response);

}