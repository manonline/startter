package com.manonline.examples.threading.threadpool.example2;

/**
 * Created by davidqi on 2/2/17.
 */
public class Controller {
    public static void main(String[] args) throws InterruptedException {

        // create a thread pool
        ThreadPool pool = ThreadPool.getThreadPool();

        Runnable r = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // start the pool
        for (int i = 0; i < 10; i++) {
            pool.start(r);
        }
        Thread.sleep(1500);
        System.out.println(pool.getThreadCount());

        // shutdown the pool
        pool.shutdown();

        Thread.sleep(1000);
        System.out.println(pool.getThreadCount());
    }
}