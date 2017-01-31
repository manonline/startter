package com.manonline.examples.threading.threadpool;

//测试线程池  
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