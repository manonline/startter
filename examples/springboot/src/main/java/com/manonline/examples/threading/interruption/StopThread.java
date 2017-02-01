package com.manonline.examples.threading.interruption;

/**
 * Created by davidqi on 2/2/17.
 * http://www.cnblogs.com/simonshi/archive/2011/12/31/2308455.html
 */
public class StopThread extends Thread {
    // the best way to stop a thread is to check a shared variable, like below to give the thread a chance to
    // clean up things properly, rather than using Thread.interrupt() that only changes the state of isInterruptted
    volatile boolean stop = false;

    public static void main(String args[]) throws Exception {
        // start child thread
        StopThread thread = new StopThread();
        System.out.println("Starting thread...");
        thread.start();

        Thread.sleep(3000);

        // stop the chhild thread - by changing the shared variable. Let child thread to figure out how to stop
        System.out.println("Asking thread to stop...");
        thread.stop = true;

        Thread.sleep(3000);

        // exit
        System.out.println("Stopping application...");
    }

    public void run() {
        // check the shared variable on regular basis
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            while ((System.currentTimeMillis() - time < 1000) && (!stop)) {
            }
        }
        System.out.println("Thread exiting under request...");
    }
}
