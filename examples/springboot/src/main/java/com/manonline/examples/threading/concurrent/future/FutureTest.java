package com.manonline.examples.threading.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FutureTask也可以用作闭锁。它表示一种抽象的可生成结果的计算。是通过 Callable 来实现的，相当于一种可生成结果的 Runnable，
 * 并且可处于以下三种状态：等待运行，正在运行，运行完成。当FutureTask进入完成状态后，它会停留在这个状态上。 Future.get
 * 用来获取计算结果，如果FutureTask还未运行完成，则会阻塞。FutureTask
 * 将计算结果从执行计算的线程传递到获取这个结果的线程，而FutureTask 的规范确保了这种传递过程能实现结果的安全发布。
 * FutureTask在Executor框架中表示异步任务，还可以用来表示一些时间较长的计算，这些计算可以在使用计算结果之前启动。
 * 下面我们来构造一个简单的异步任务，来简单示范FutureTask的使用方法。
 */
public class FutureTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);
        executor.shutdown();
         
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
         
        System.out.println("主线程在执行任务");
         
        try {
            System.out.println("task运行结果" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
         
        System.out.println("所有任务执行完毕");
    }
}
class Task implements Callable<Integer>{
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        return sum;
    }
}