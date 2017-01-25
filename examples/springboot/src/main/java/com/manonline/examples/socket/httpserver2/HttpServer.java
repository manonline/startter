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

        // set server port
        int port;
        try {
            port = Integer.parseInt(args[0]);
            if (port < 0 || port > 65535) {
                port = 8080;
            }
        } catch(Exception e) {
            System.out.println("port = 8080 (Default)");
            port = 8080;
        }

        try {
            // create a SserverSocket
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                try {
                    // accepting request
                    /**
                     * NOTE : this is a single thread server. before a request is served, i.e. service() returns,
                     * serverSocket.accept() won't get executed, in another word, new request won't be accepted.
                     */
                    final Socket socket = serverSocket.accept();
                    System.out.println("Accepting connections on port " + port + serverSocket.getLocalPort());
                    System.out.println("Client Address is " + socket.getInetAddress() + ":" + socket.getLocalPort());

                    // serving the request
                    /**
                     * NOTE : passing the socket (returned/created by serverSocket.accept()), as a way to pass request
                     * and response, essentially request is socket.InputStream and response is socket.OutputStream
                     */
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
        // get InputStream, i.e. request
        InputStream socketIn = socket.getInputStream();
        Thread.sleep(500); // no use, just for this example;

        // read into request string
        int size = socketIn.available();
        byte[] buffer = new byte[size];
        socketIn.read(buffer);
        String request = new String(buffer);
        System.out.println(request);

        // get the uri from the first line of request
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1];

        // based uri to determine contentType
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

        // send response via OutputStream, i.e. response
        OutputStream socketOut = socket.getOutputStream();
        String responseFirstLine = "HTTP/1.1 200 OK \r\n";
        String responseHeader = "Content-Type" + contentType + "\r\n\r\n";
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());

        // compose the response body
        InputStream in = HttpServer.class.getResourceAsStream("root/" +  uri); // only an example
        int len = 0;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer, 0, len);
        }

        Thread.sleep(1000); // for this example only
        socket.close();
    }
}
