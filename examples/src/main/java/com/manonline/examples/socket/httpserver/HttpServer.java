package com.manonline.examples.socket.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davidqi on 1/23/17.
 */
public class HttpServer {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getCanonicalName());

    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";

    private final File rootDirectory;
    private final int port;

    public HttpServer(File rootDirectory, int port) throws IOException {
        // validate input parameters
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + "does not exist as a Directory");
        }
        // set root and port
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    public void start() throws IOException {
        // setup
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + port + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);

            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Error accepting connection", ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        // set document root
        File docroot;
        try {
            docroot = new File(args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Usage: java HttpServer docroot port");
            return;
        }

        // set port
        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) {
                port = 80;
            }
        } catch (RuntimeException ex) {
            port = 80;
        }

        // start the server
        try {
            HttpServer webserver = new HttpServer(docroot, port);
            webserver.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Server could not start", ex);
        }
    }
}