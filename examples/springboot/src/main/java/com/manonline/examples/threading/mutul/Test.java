package com.manonline.examples.threading.mutul;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        CThread cThread0 = new CThread();
        Thread t1 = new Thread(cThread0, "Thread 1");
        Thread t2 = new Thread(cThread0, "Thread 2");
        Thread t3 = new Thread(cThread0, "Thread 3");
        
        CThread cThread1 = new CThread();
        CThread cThread2 = new CThread();
        
        t1.start();
        t2.start();
        t3.start();
        
    }
    
    public int add(int a, int b) {
        return a + b;
    }

}
