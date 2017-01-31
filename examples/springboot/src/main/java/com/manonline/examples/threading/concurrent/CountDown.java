package com.manonline.examples.threading.concurrent;

/**
 * 
闭锁作用相当于一扇门：在闭锁到达某一状态之前，这扇门一直是关闭的，所有的线程都会在这扇门前等待（阻塞）。只有门打开后，所有的线程才会同时继续运行。
闭锁可以用来确保某些活动直到其它活动都完成后才继续执行，例如：
1、确保某个计算在其所有资源都被初始化之后才继续执行。二元闭锁（只有两个状态）可以用来表示“资源R已经被初始化”，而所有需要R操作都必须先在这个闭锁上等待。
2、确保某个服务在所有其他服务都已经启动之后才启动。这时就需要多个闭锁。让S在每个闭锁上等待，只有所有的闭锁都打开后才会继续运行。
3、等待直到某个操作的参与者（例如，多玩家游戏中的玩家）都就绪再继续执行。在这种情况下，当所有玩家都准备就绪时，闭锁将到达结束状态。
CountDownLatch 是一种灵活的闭锁实现，可以用在上述各种情况中使用。闭锁状态包含一个计数器，初始化为一个正数，表示要等待的事件数量。countDown() 方法会递减计数器，表示等待的事件中发生了一件。await() 方法则阻塞，直到计数器值变为0。
下面，我们使用闭锁来实现在主线程中计算多个子线程运行时间的功能。具体逻辑是使用两个闭锁，“起始门”用来控制子线程同时运行，“结束门”用来标识子线程是否都结束。
 */
import java.util.concurrent.CountDownLatch;  

public class CountDown {  
    public static int nThread = 5;  
    public static void main(String[] args) throws InterruptedException{  
        final CountDownLatch startGate = new CountDownLatch(1);  
        final CountDownLatch endGate = new CountDownLatch(nThread);  
        for(int i =0;i<nThread;i++){  
            new Thread(){  
                @Override  
                public void run(){  
                    try{  
                    startGate.await();  
                    Thread.sleep(300);}  
                    catch(InterruptedException e){  
                    }  
                    finally{  
                        System.out.println(Thread.currentThread().getName()+"ended");  
                        endGate.countDown();  
                    }  
                }  
            }.start();  
        }  
        long start = System.nanoTime();  
        startGate.countDown();  
        endGate.await();  
        long end = System.nanoTime();  
        System.out.println("Total time :"+(end - start));  
    }  
}  