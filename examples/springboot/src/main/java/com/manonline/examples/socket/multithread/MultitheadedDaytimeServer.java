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
public class MultitheadedDaytimeServer {
    public static final int PORT = 13;

    public static void main(String args[]) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    /**
                     * Note : serving the request in a new thread, so the current thread can run and start accepting
                     * new request; the only thing passed to the new thread is a connection, i.e. socket, and thread
                     * can get the input/output stream
                     */
                    Thread task = new DaytimeThread(connection);
                    task.start();
                } catch (IOException ex) {
                    // handle exceptions
                }
            }
        } catch (IOException e) {
            System.err.println("Couldn't start server");
        }
    }

    private static class DaytimeThread extends Thread {
        private Socket connection;

        DaytimeThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                // in this thread, get the connection, parse the input and send the response
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    connection.close();
                } catch (IOException ex) {
                    // handle exceptions
                }
            }
        }
    }
}
