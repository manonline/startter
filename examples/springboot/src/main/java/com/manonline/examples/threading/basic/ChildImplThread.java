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
}
