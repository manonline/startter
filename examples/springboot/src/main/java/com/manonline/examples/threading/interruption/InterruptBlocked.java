package com.manonline.examples.threading.interruption;

/**
 * Created by davidqi on 2/1/17.
 * http://blog.csdn.net/sunxing007/article/details/9123363
 * 不是所有的阻塞方法收到中断后都可以取消阻塞状态,
 * -- 输入和输出流类会阻塞等待I/O完成，但是它们不抛出InterruptedException，而且在被中断的情况下也不会退出阻塞状态.
 * -- 尝试获取一个内部锁的操作（进入一个 synchronized 块）是不能被中断的，但是ReentrantLock支持可中断的获取模式即
 * tryLock(long time, TimeUnit unit)。
 */
public class InterruptBlocked extends Thread {

    private Thread parent;

    public InterruptBlocked(Thread parent) {
        this.parent = parent;
    }

    public static void main(String[] args) {
        InterruptBlocked child = new InterruptBlocked(Thread.currentThread());
        child.start();

        try {
            // waiting child to finish ...
            child.join();
        } catch (InterruptedException e) {
            System.out.println("parent is awake ...");
        }
    }

    public void run() {
        while (true) {
            System.out.println("child thread is running...");
            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {
                // 为了避免Thread.sleep()而需要捕获InterruptedException而带来的理解上的困惑,
                // 此处用这种方法空转2秒
            }
            parent.interrupt();
        }
    }
}
