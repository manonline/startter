package com.manonline.examples.threading.mutul;

public class CThread extends Thread {
    
    @Override
    public void run() {
        System.out.println("Thread : " + Thread.currentThread().getName() + " Started");
        
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
    
    public int add(int a, int b) {
        return a + b;
    }
}
