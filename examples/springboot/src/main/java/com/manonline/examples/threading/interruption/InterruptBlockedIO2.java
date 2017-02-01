package com.manonline.examples.threading.interruption;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by davidqi on 2/2/17.
 */
public class InterruptBlockedIO2 extends Thread {

    volatile boolean stop = false;
    volatile ServerSocket socket;

    public static void main(String args[]) throws Exception {
        InterruptBlockedIO2 thread = new InterruptBlockedIO2();
        // start child thread
        System.out.println("Starting thread...");
        thread.start();

        Thread.sleep(3000);

        // stop, instead of interrupt, the child thread
        System.out.println("Asking thread to stop...");
        thread.stop = true;
        // using .close() to stop...
        thread.socket.close();

        Thread.sleep(3000);

        // exit
        System.out.println("Stopping application...");
    }

    public void run() {
        try {
            socket = new ServerSocket(7856);
        } catch (IOException e) {
            System.out.println("Could not create the socket...");
            return;
        }
        while (!stop) {
            System.out.println("Waiting for connection...");
            try {
                Socket sock = socket.accept();
            } catch (IOException e) {
                // gets to here due to .close()
                System.out.println("accept() failed or interrupted...");
            }
        }
        System.out.println("Thread exiting under request...");
    }
}
