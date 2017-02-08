package com.manonline.examples.threading.concurrent.future;

/**
 * Created by davidqi on 2/2/17.
 * 在Java中，如果需要设定代码执行的最长时间，即超时，可以用Java线程池ExecutorService类配合Future接口来实现。
 * Future接口是Java标准API的一部分，在java.util.concurrent包中。Future接口是Java线程Future模式的实现，
 * 可以来进行异步计算。
 * <p>
 * Future模式可以这样来描述：我有一个任务，提交给了Future，Future替我完成这个任务。期间我自己可以去做任何想做的事情。
 * 一段时间之后，我就便可以从Future那儿取出结果。就相当于下了一张订货单，一段时间后可以拿着提订单来提货，这期间可以干别
 * 的任何事情。其中Future 接口就是订货单，真正处理订单的是Executor类，它根据Future接口的要求来生产产品。
 * <p>
 * Future接口提供方法来检测任务是否被执行完，等待任务执行完获得结果，也可以设置任务执行的超时时间。这个设置超时的方法就
 * 是实现Java程序执行超时的关键。
 * <p>
 * Future的实现类有java.util.concurrent.FutureTask<V>即 javax.swing.SwingWorker<T,V>。通常使用FutureTask来处
 * 理我们的任务。FutureTask类同时又实现了Runnable接口，所以可以直接提交给Executor执行。其实FutureTask是一个Thread的
 * 包装盒，并通过Future接口来和Therad进行交互。
 * <p>
 * 不直接构造Future对象，也可以使用ExecutorService.submit方法来获得Future对象，submit方法即支持以 Callable接口类型，
 * 也支持Runnable接口作为参数，具有很大的灵活性。
 */

import java.util.concurrent.*;

public class RunnableAndCallable2Future {

    public static void main(String[] args) {

        // 创建一个执行任务的服务
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            /**
             * 0.
             */
            FutureTaskCallable0 task0 = new FutureTaskCallable0();
            FutureTask<String> futureTask = new FutureTask<String>(task0);
            executor.submit(futureTask);
            executor.shutdown();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            System.out.println("主线程在执行任务");

            try {
                System.out.println("task运行结果" + futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("所有任务执行完毕");

            /**
             * 1.Runnable通过Future返回结果为空
             * 创建一个Runnable，来调度，等待任务执行完毕，取得返回结果
             */
            Future<?> runnable1 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("runnable1 running.");
                }
            });
            System.out.println("Runnable1:" + runnable1.get());

            /**
             * 2. Callable通过Future能返回结果
             * 提交并执行任务，任务启动时返回了一个 Future对象，如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
             */
            Future<String> future1 = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // TODO Auto-generated method stub
                    return "result=task1";
                }
            });
            System.out.println("task1: " + future1.get());

            /**
             * 3. 对Callable调用cancel可以对对该任务进行中断
             * 提交并执行任务，任务启动时返回了一个 Future对象, 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
             */
            Future<String> future2 = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        while (true) {
                            System.out.println("task2 running.");
                            Thread.sleep(50);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted task2.");
                    }
                    return "task2=false";
                }
            });
            // 等待5秒后，再停止第二个任务。因为第二个任务进行的是无限循环
            Thread.sleep(5000);
            System.out.println("task2 cancel: " + future2.cancel(true));

            /**
             * 4. 用Callable时抛出异常则Future什么也取不到了
             * 获取第三个任务的输出，因为执行第三个任务会引起异常, 所以下面的语句将引起异常的抛出
             */
            Future<String> future3 = executor.submit(new Callable<String>() {

                @Override
                public String call() throws Exception {
                    throw new Exception("task3 throw exception!");
                }

            });
            System.out.println("task3: " + future3.get());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // 停止任务执行服务
        executor.shutdownNow();
    }

    static class FutureTaskCallable0 implements Callable<String> {
        public String call() throws Exception {
            Thread.sleep(3000);
            return "Future Task Callable 0....";
        }
    }
}