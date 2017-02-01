package com.manonline.examples.threading.interruption;

/**
 * Created by davidqi on 2/1/17.
 */
public class InterruptUnblocked extends Thread {

    public static void main(String[] args) throws InterruptedException {
        InterruptUnblocked child = new InterruptUnblocked();
        child.start();
        // main thread sleep 3 seconds
        Thread.sleep(3000);
        // then interrupt child thread
        child.interrupt();
    }

    public void run() {
        // child thread keep running ...
        while (true) {
            if (Thread.interrupted()) {
                // get interrupted by main thread who called child.interrupt() that changed the Thread.interrupted
                // to true
                System.out.println("Someone interrupted me.");
            } else {
                System.out.println("Running ...");
            }

            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 1000) {
                // 为了避免Thread.sleep()而需要捕获InterruptedException而带来的理解上的困惑,
                // 此处用这种方法空转1秒
            }
        }
    }
}