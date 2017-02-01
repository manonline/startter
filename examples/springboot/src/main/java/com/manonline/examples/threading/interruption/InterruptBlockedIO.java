package com.manonline.examples.threading.interruption;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by davidqi on 2/2/17.
 */
public class InterruptBlockedIO extends Thread {

    public static void main(String args[]) throws Exception {
        InterruptBlockedIO thread = new InterruptBlockedIO();
        // start child thread
        System.out.println("Starting thread...");
        thread.start();

        Thread.sleep(3000);

        // interrupt child thread - but child thread will still be in blocked state...
        System.out.println("Interrupting thread...");
        thread.interrupt();

        Thread.sleep(3000);

        // exit
        System.out.println("Stopping application...");
    }

    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(7856);
        } catch (IOException e) {
            System.out.println("Could not create the socket...");
            return;
        }

        while (true) {
            System.out.println("Waiting for connection...");
            try {
                Socket sock = socket.accept();
            } catch (IOException e) {
                System.out.println("accept() failed or interrupted...");
            }
        }
    }
}
