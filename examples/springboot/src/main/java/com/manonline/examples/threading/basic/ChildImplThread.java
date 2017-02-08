package com.manonline.examples.threading.basic;

public class ChildImplThread implements Runnable {

    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("CHILD THREAD IMPLEMENTED Runnable : " + i);
        }
    }

    public static void main(String args[]) {

        // construct an object that implements Runnable interface
        Runnable r1 = new ChildImplThread();
        // construct a Thread object with an object implmented runnable interface,
        // i.e. implemented run() function;
        Thread t1 = new Thread(r1);
        t1.start();
    }
}
