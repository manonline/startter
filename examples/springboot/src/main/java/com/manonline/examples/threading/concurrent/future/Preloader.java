package com.manonline.examples.threading.concurrent.future;

/**
 * Created by davidqi on 2/2/17.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {
    private final FutureTask<Integer> future = new FutureTask<Integer>(
            new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(3000);
                    return 969;
                }
            });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public Integer get() throws Exception {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            throw launderThrowable(cause);
        }
    }

    private static Exception launderThrowable(Throwable cause) {
        if (cause instanceof RuntimeException) return (RuntimeException) cause;
        else if (cause instanceof Error) throw (Error) cause;
        else throw new IllegalStateException("Not Checked", cause);
    }

    public static void main(String[] args) throws Exception {
        Preloader p = new Preloader();
        p.start();
        long start = System.currentTimeMillis();
        System.out.println(p.get());
        System.out.println(System.currentTimeMillis() - start);
    }
}