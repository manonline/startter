package com.manonline.examples.threading.basic;

public class ChildExtThread extends Thread {
    
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("CHILD THREAD EXTENDED Thread : " + i);
        }
    }

    public static void main(String args[]) {
        Thread t1 = new ChildExtThread();
        /**
         * 一个Thread的实例一旦调用start()方法，这个实例的started标记就标记为true，事实中不管这个线程后来有没有执行到底，
         * 只要调用了一次start()就再也没有机会运行了。
         */
        t1.start();
    }

}
