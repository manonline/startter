package com.manonline.examples.threading.threadpool.example2;

import java.util.List;
import java.util.Vector;

/**
 * Created by davidqi on 2/2/17.
 */
public class ThreadPool {
    // 使用单例模式创建线程池
    private static ThreadPool pool;

    private final List<PThread> threads;
    private volatile boolean isShutdown;
    private int threadCount = 0;

    // 初始化一个大小为5的线程池数组
    synchronized public static ThreadPool getThreadPool() {
        // Singleton
        if (pool == null) {
            pool = new ThreadPool(5);
        }
        return pool;
    }
    private ThreadPool(int poolSize) {
        threads = new Vector<PThread>(poolSize);
        isShutdown = false;
    }

    // 在线程中调用，用来将自己加入线程池中
    synchronized public void putThread(PThread t) {
        if (!isShutdown) {
            threads.add(t);
        } else {
            t.shutdown();
        }
    }

    // 客户端用来执行自己的一项任务
    synchronized public void start(Runnable task) {
        if (!isShutdown) {
            if (threads.size() < 1) {
                new PThread(task, pool).start();
            } else {
                PThread p = threads.remove(0);
                //设置好
                p.setTarget(task);
            }
        }
    }

    // 获得线程池中线程的数量
    public int getThreadCount() {
        return threads.size();
    }

    // 如果关闭线程池，需要将所有线程也关闭
    synchronized public void shutdown() {
        for (PThread p : threads)
            p.shutdown();
        threads.clear();
        isShutdown = true;
    }


}