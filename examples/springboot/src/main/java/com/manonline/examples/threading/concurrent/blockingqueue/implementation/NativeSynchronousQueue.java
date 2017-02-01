package com.manonline.examples.threading.concurrent.blockingqueue.implementation;

/**
 * Created by davidqi on 2/1/17.
 */
public class NativeSynchronousQueue<E> {
    boolean putting = false;
    E item = null;

    /**
     * Note :
     * 1. synchronized
     * 2. this is for consumer
     */
    public synchronized E take() throws InterruptedException {
        while (item == null) {
            wait();
        }
        E e = item;
        item = null;

        // notify all the threads that are waiting on this object, that there is a change on object state
        notifyAll();
        return e;
    }

    /**
     * Note :
     * 1. synchronized
     * 2. this is for consumer
     */
    public synchronized void put(E e) throws InterruptedException {
        if (e == null) {
            return;
        }

        while (putting) {
            wait();
        }
        putting = true;
        item = e;

        // notify all the threads that are waiting on this object, that there is a change on object state
        notifyAll();

        while (item != null) {
            wait();
        }
        putting = false;

        // notify all the threads that are waiting on this object, that there is a change on object state
        notifyAll();
    }
}