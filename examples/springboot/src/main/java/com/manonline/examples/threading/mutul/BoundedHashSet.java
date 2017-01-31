package com.manonline.examples.threading.mutul;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private final Set<T> set;
    private Semaphore sem;

    public BoundedHashSet(int bound) {
        if (bound < 1)
            throw new IllegalStateException();
        set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T e) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(e);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(T e) {
        boolean wasRemoved = set.remove(e);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }

}