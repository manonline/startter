package com.manonline.examples.threading.threadpool.example2;

/**
 * Created by davidqi on 2/2/17.
 */
public class WorkThread extends Thread {
    private final ThreadPool pool;
    private volatile boolean isShutdown;
    private Runnable task;

    public WorkThread(Runnable task, ThreadPool pool) {
        this.task = task;
        this.pool = pool;
        isShutdown = false;
    }

    @Override
    public void run() {
        while (!isShutdown) {
            if (task != null) {
                task.run();
            }
            try {
                // 线程执行完任务之后被放入线程池
                pool.putThread(this);
                // 线程执行完任务，会在wait处阻塞，直到 setTarget或者 shutdown 调用notifyAll
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    // 每当设置任务或关闭时会唤醒run方法
    synchronized public void setTarget(Runnable r) {
        this.task = r;
        // 唤醒等待中的线程，也就是这个线程对象所构建的线程，因为这个锁是加在线程对象上的。
        notifyAll();
    }

    synchronized public void shutdown() {
        isShutdown = true;
        notifyAll();
    }

}