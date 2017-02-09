package com.manonline.examples.threading.concurrent.semaphore;

/**
 * 之前讲的闭锁控制访问的时间，而信号量则用来控制访问某个特定资源的操作数量，控制空间。而且闭锁只能够减少，一次性使用，
 * 而信号量则申请可释放，可增可减。 计数信号量还可以用来实现某种资源池，或者对容器施加边界。
 * Semaphone 管理这一组许可（permit），可通过构造函数指定。同时提供了阻塞方法acquire，用来获取许可。
 * 同时提供了release方法表示释放一个许可。
 * Semaphone 可以将任何一种容器变为有界阻塞容器，如用于实现资源池。例如数据库连接池。我们可以构造一个固定长度的连接池，
 * 使用阻塞方法 acquire和release获取释放连接，而不是获取不到便失败。
 * （当然，一开始设计时就使用BlockingQueue来保存连接池的资源是一种更简单的方法）例如我们将一个普通set容器变为可阻塞有界。
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SemaphoreTest<T> {
    private final Set<T> set;
    private Semaphore sem;

    public SemaphoreTest(int bound) {
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