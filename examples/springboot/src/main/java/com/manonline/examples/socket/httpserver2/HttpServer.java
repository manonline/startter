package com.manonline.examples.socket.httpserver2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by davidqi on 1/23/17.
 */
public class HttpServer {
    public static void main(String args[]) {
        int port;
        ServerSocket serverSocket;

        try {
            port = Integer.parseInt(args[0]);
        } catch(Exception e) {
            System.out.println("port = 8080 (Default)");
            port = 8080;
        }

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    // accepting request
                    final Socket socket = serverSocket.accept();
                    System.out.println("Accepting connections on port " + port + serverSocket.getLocalPort());
                    System.out.println("Client Address is " + socket.getInetAddress() + ":" + socket.getLocalPort());

                    // serving the request
                    service(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void service(Socket socket) throws Exception {
        InputStream socketIn = socket.getInputStream();
        Thread.sleep(500);
        int size = socketIn.available();
        byte[] buffer = new byte[size];
        socketIn.read(buffer);
        String request = new String(buffer);
        System.out.println(request);

        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1];

        String contentType;
        if (uri.indexOf("html") != -1 || uri.indexOf("htm") != -1) {
            contentType = "text/html";
        } else if (uri.indexOf("jpg") != -1 || uri.indexOf("jpeg") != -1) {
            contentType = "image/jpeg";
        } else if (uri.indexOf("gif") != -1) {
            contentType = "image/gif";
        } else {
            contentType = "application/octet-stream";
        }

        String responseFirstLine = "HTTP/1.1 200 OK \r\n";
        String responseHeader = "Content-Type" + contentType + "\r\n\r\n";
        InputStream in = HttpServer.class.getResourceAsStream("root/" +  uri);

        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());

        int len = 0;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer, 0, len);
            Thread.sleep(1000);
            socket.close();
        }
    }
}
