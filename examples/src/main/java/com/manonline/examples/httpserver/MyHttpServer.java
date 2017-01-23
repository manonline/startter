package com.manonline.examples.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class MyHttpServer {

    public static void start() throws IOException {

        // create http server with 100 threads, listening on 8080 port
        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(8080), 100);

        // load the context
        Context.load();
        httpserver.createContext(Context.contextPath, new MyHttpHandler());

        // other config
        httpserver.setExecutor(null);

        // run
        httpserver.start();

        System.out.println("server started");
    }

    public static void main(String[] args) throws IOException {
        start();
    }
}