package com.manonline.examples.threading.threadpool;

//测试线程池

/**
 * 1. 初识线程池：根据系统自身的环境情况，有效的限制执行线程的数量，使得运行效果达到最佳。线程主要是通过控制执行的线程的数量，
 *    超出数量的线程排队等候，等待有任务执行完毕，再从队列最前面取出任务执行。
 * 2. 线程池作用：减少创建和销毁线程的次数，每个工作线程可以多次使用可根据系统情况调整执行的线程数量，防止消耗过多内存
 * 3. 使用
 *    ExecutorService:线程池接口
 *    ExecutorService pool = Executors.常见线程
 *    ExecutorService pool = Executors.newSingleThreadExecutor();
 * 4. 常见线程池
 * 4.1 newSingleThreadExecutor ：单个线程的线程池，即线程池中每次只有一个线程工作，单线程串行执行任务
 * 4.2 newFixedThreadExecutor(n) ：固定数量的线程池，每提交一个任务就是一个线程，直到达到线程池的最大数量，然后后面进入等待
 * 队列直到前面的任务完成才继续执行
 * 4.3 newCacheThreadExecutor(推荐使用) ：可缓存线程池，当线程池大小超过了处理任务所需的线程，那么就会回收部分空闲（一般是
 * 60秒无执行）的线程，当有任务来时，又智能的添加新线程来执行。
 * 4.4 newScheduleThreadExecutor ： 大小无限制的线程池，支持定时和周期性的执行线程
 */
public class Controller {  
  public static void main(String[] args) {  
      // 创建3个线程的线程池  
      ThreadPool t = ThreadPool.getThreadPool(3);  
      System.out.println(Thread.currentThread().getName());

      t.execute(new Runnable[] { new Task(), new Task(), new Task(), new Task() ,new Task() ,new Task() ,new Task() ,new Task() ,new Task() ,new Task() ,new Task() ,new Task(), new Task() ,});
      t.execute(new Runnable[] { new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task(), new Task() });  
      System.out.println(t);  

      t.destroy();// 所有线程都执行完成才destory
      System.out.println(t);  
  }  

  // 任务类  
  static class Task implements Runnable {  
      private static volatile int i = 1;  

      public void run() {
          // 执行任务  
          System.out.println("任务 " + (i++) + "被 " + Thread.currentThread().getName() + " 完成");  
      }  
  }  
}