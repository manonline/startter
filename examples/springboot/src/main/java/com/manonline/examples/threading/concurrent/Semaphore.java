package com.manonline.examples.threading.concurrent;

/**
 * 之前讲的闭锁控制访问的时间，而信号量则用来控制访问某个特定资源的操作数量，控制空间。而且闭锁只能够减少，
 * 一次性使用，而信号量则申请可释放，可增可减。 计数信号量还可以用来实现某种资源池，或者对容器施加边界。
 * Semaphone 管理这一组许可（permit），可通过构造函数指定。同时提供了阻塞方法acquire，用来获取许可。
 * 同时提供了release方法表示释放一个许可。
 * Semaphone 可以将任何一种容器变为有界阻塞容器，如用于实现资源池。例如数据库连接池。我们可以构造一个固定长度的连接池，
 * 使用阻塞方法 acquire和release获取释放连接，而不是获取不到便失败。
 * （当然，一开始设计时就使用BlockingQueue来保存连接池的资源是一种更简单的方法）例如我们将一个普通set容器变为可阻塞有界。
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Semaphore {
    private final FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
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
        if (cause instanceof RuntimeException)
            return (RuntimeException) cause;
        else if (cause instanceof Error)
            throw (Error) cause;
        else
            throw new IllegalStateException("Not Checked", cause);
    }

    public static void main(String[] args) throws Exception {
        Semaphore p = new Semaphore();
        p.start();
        long start = System.currentTimeMillis();
        System.out.println(p.get());
        System.out.println(System.currentTimeMillis() - start);
    }
}