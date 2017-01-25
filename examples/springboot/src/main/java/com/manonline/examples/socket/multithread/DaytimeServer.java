package com.manonline.examples.socket.multithread;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by davidqi on 1/23/17.
 */
public class DaytimeServer {
    public static final int PORT = 13;

    public static void main(String args[]) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket connection = server.accept()) {
                    /**
                     * Note : focus on serving the request in the same thread and cannot
                     * accept a new request before the current request is served
                     */
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.write(now.toString() + "\r\n");
                    out.flush();
                    connection.close();
                } catch (IOException ex) {
                    // handle exceptions
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}