package com.manonline.examples.threading;

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

}
