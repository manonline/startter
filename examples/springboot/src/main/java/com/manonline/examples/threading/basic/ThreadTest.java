package com.manonline.examples.threading.basic;

public class ThreadTest {
    /**
     * public class Thread implement Runnable { public Thread(ThreadGroup group,
     * Runnable target, String name) { init(group, target, name, 0); }
     * 
     * public void start() { ....; } // run target class which implements
     * Runnable interface, i.e. run() // run empty run, if target class is
     * empty, i.e. no target passed in. // run overridden run from sub class ..,
     * but target is empty. public void run() { if (target != null) {
     * target.run(); } } }
     * 在一个运行系统中，如果较高优先级的线程没有调用sleep方法，也没有受到I/O阻塞，那么较低优先级线程只能等待所有较高优先级的线程运行结束，方可有机会运行。
     * 
     * yield()只是使当前线程重新回到可执行状态，所有执行yield()的线程有可能在进入到可执行状态后马上又被执行，所以yield()方法只能使同优先级的线程有执行的机会。
     * join()方法使调用该方法的线程在此之前执行完毕，也就是等待该方法的线程执行完毕后再往下继续执行。注意该方法也需要捕捉异常。
     * wait()和notify()、notifyAll()
     * 
     * 这三个方法用于协调多个线程对共享数据的存取，所以必须在synchronized语句块内使用。synchronized关键字用于保护共享数据，阻止其他线程对共享数据的存取，但是这样程序的流程就很不灵活了，
     * 如何才能在当前线程还没退出synchronized数据块时让其他线程也有机会访问共享数据呢？此时就用这三个方法来灵活控制。
     * wait()方法使当前线程暂停执行并释放对象锁标示，让其他线程可以进入synchronized数据块，当前线程被放入对象等待池中。当调用notify()方法后，将从对象的等待池中移走一个任意的线程并放到锁标志等待池中，只有锁标志等待池中线程能够获取锁标志；如果锁标志等待池中没有线程，则notify()不起作用。
     * notifyAll()则从对象等待池中移走所有等待那个对象的线程并放到锁标志等待池中。
     * sleep(long)使当前线程进入停滞状态，所以执行sleep()的线程在指定的时间内肯定不会被执行；
     * sleep(long)可使优先级低的线程得到执行的机会，当然也可以让同优先级的线程有执行的机会； sleep(long)是不会释放锁标志的。
     * 
     */
    public static void main(String[] args) {

        startUp();

        threadTesting1();

        ChildImplThread cImplThread = new ChildImplThread();
        new Thread(cImplThread).start();

        new ChildExtThread().start();
    }

    private static void startUp() {
        System.out.println("==================================================================");
        System.out.println("[Runtime ID         ]: " + Runtime.getRuntime());
        System.out.println("[Runtime Ttl Memory ]: " + Runtime.getRuntime().totalMemory());
        System.out.println("[Runtime Max Memory ]: " + Runtime.getRuntime().maxMemory());
        System.out.println("[Runtime Processors ]: " + Runtime.getRuntime().availableProcessors());
        System.out.println("==================================================================");
        System.out.println("[Thread Name        ]: " + Thread.currentThread().getName());
        System.out.println("[Thread ID          ]: " + Thread.currentThread().getId());
        System.out.println("[Thread Group       ]: " + Thread.currentThread().getThreadGroup());
        System.out.println("[Thread Priority    ]: " + Thread.currentThread().getPriority());
        System.out.println("[Thread ClassLoader ]: " + Thread.currentThread().getContextClassLoader());
        System.out.println("[Thread State       ]: " + Thread.currentThread().getState());
        System.out.println("[Thread is Alive    ]: " + Thread.currentThread().isAlive());
        System.out.println("[Thread is Daemon   ]: " + Thread.currentThread().isDaemon());
        System.out.println("==================================================================");
        System.out.println("[Class Info         ]: " + ThreadTest.class);
        System.out.println("==================================================================");
    }

    private static void threadTesting1() {
        Thread thread = new ThreadDemo();
        // 第一种
        // 表明: run()和其他方法的调用没任何不同,main方法按顺序执行了它,并打印出最后一句
        // thread.run();

        // 第二种
        // 表明: start()方法重新创建了一个线程,在main方法执行结束后,由于start()方法创建的线程没有运行结束,
        // 因此主线程未能退出,直到线程thread也执行完毕.这里要注意,默认创建的线程是用户线程(非守护线程)
        thread.start();

        // 第三种
        // 1、为什么没有打印出100句呢?因为我们将thread线程设置为了daemon(守护)线程,程序中只有守护线程存在的时候,是可以退出的,所以只打印了七句便退出了
        // 2、当java虚拟机中有守护线程在运行的时候，java虚拟机会关闭。当所有常规线程运行完毕以后，
        // 守护线程不管运行到哪里，虚拟机都会退出运行。所以你的守护线程最好不要写一些会影响程序的业务逻辑。否则无法预料程序到底会出现什么问题
        // thread.setDaemon(true);
        // thread.start();

        // 第四种
        // 用户线程可以被System.exit(0)强制kill掉,所以也只打印出七句
        // thread.start();
        // System.out.println("main thread is over");
        // System.exit(1);
    }

    public static class ThreadDemo extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("CHILD THREAD CREATED FROM MAIN : " + i);
            }
        }
    }
}