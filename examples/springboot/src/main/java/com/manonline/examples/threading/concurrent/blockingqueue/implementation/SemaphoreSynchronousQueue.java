package com.manonline.examples.threading.concurrent.blockingqueue.implementation;

import java.util.concurrent.Semaphore;

/**
 * Created by davidqi on 2/1/17.
 */
public class SemaphoreSynchronousQueue<E> {
    E item = null;
    Semaphore sync = new Semaphore(0);
    Semaphore send = new Semaphore(1);
    Semaphore recv = new Semaphore(0);

    public E take() throws InterruptedException {
        recv.acquire();
        E x = item;
        sync.release();
        send.release();
        return x;
    }

    public void put(E x) throws InterruptedException {
        send.acquire();
        item = x;
        recv.release();
        sync.acquire();
    }
}