package com.manonline.examples.httpserver.core;

import java.net.URI;

/**
 * Created by davidqi on 1/23/17.
 */
public interface Request {
    public final static String GET = "GET";
    public final static String POST = "POST";

    public String getParamter(String param);

    public String getMethod();

    public URI getReuestURI();

    public void initRequestHeader();

    public void initRequestParam();

    public void initRequestBody();

    public String getRequestBody();
}